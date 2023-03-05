package river.chat.lib_core.net.request

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import river.chat.lib_core.net.bean.BaseRequestBean
import river.chat.lib_core.net.bean.PageModel
import river.chat.lib_core.net.common.NetRespoonseEnum
import river.chat.lib_core.net.common.OperationException
import river.chat.lib_core.utils.longan.isNetworkAvailable
import river.chat.lib_core.utils.longan.toast

/**
 * Created on 2021/10/27
 * @author BeiYongchao
 * @descripyion: 请求基类
 */
open class BaseRequest(var viewModel: BaseViewModel) {

    private var mCurrentStatus = ViewStatus.UN_KNOW

    /**
     * flow 请求
     * @param request 请求体
     * @param dataResp 成功回调 响应
     * @param error 失败回调
     * @param complete  完成回调（无论成功失败都会调用）
     * @param pageResp  有分页数据时候返回的分页数据
     */
    fun <T> launchFlow(
        request: suspend CoroutineScope.() -> BaseRequestBean<T>,
        dataResp: (T?) -> Unit,
        error: (Throwable) -> Unit = {},
        complete: () -> Unit = {},
        isShowDialog: Boolean = false,
        pageResp: (PageModel?) -> Unit = {},
    ) {
        onLaunchFlow(request, dataResp, error, complete, isShowDialog, pageResp)
    }


    private fun <T> onLaunchFlow(
        request: suspend CoroutineScope.() -> BaseRequestBean<T>,
        dataResp: (T?) -> Unit,
        error: (Throwable) -> Unit = {},
        complete: () -> Unit = {},
        isShowDialog: Boolean = false,
        pageResp: (PageModel?) -> Unit = {},
    ) {
        if (prepare(error, isShowDialog)) {
            end(isShowDialog, complete)
            return
        }
        viewModel.viewModelScope.launch {
            flow { emit(request()) }
                .flowOn(Dispatchers.IO)
                .catch { t: Throwable ->
                    catch(error, t)
                    end(isShowDialog, complete)
                }
                .collect {
                    try {
                        handlerCode(it, dataResp, error, pageResp)
                        end(isShowDialog, complete)
                    } catch (t: Throwable) {
                        catch(error, t)
                    }

                }
        }
    }

    /**
     * 请求之前操作
     */
    private fun prepare(
        error: (Throwable) -> Unit = {},
        isShowDialog: Boolean
    ): Boolean {
        if (!isNetworkAvailable) {
            onStatusChanged(ViewStatus.NET_LIMIT)
            error(OperationException(RequestStatus.Msg.NET_LIMIT))
            return true
        }
        if (isShowDialog) {
            viewModel.uiObserver.showDialog.call()
        }
        return false
    }


    /**
     * 响应处理
     */
    private fun <T> handlerCode(
        responseModel: BaseRequestBean<T>,
        resp: (T?) -> Unit,
        error: (Throwable) -> Unit,
        pageRes: (PageModel?) -> Unit = {},
    ) {
        val status: String = responseModel.status
        if (NetRespoonseEnum.SUCCESS.code == status) {
            onStatusChanged(ViewStatus.SUCCESS)
            pageRes(responseModel.pageModel)
            resp(responseModel.data)
        } else {
            if (NetRespoonseEnum.TOKEN_DATE_INVALID.code == status) {
                notifyTokenExpired()
            }
            val errorMsg: String? = responseModel.message
            val exception = OperationException(status, errorMsg)
            onStatusChanged(ViewStatus.FAIL)
            error(exception)
        }
    }


    ///////////////////////////////////////////////////////////////////////////
    // 内部方法
    ///////////////////////////////////////////////////////////////////////////
    /**
     * 异常处理
     */
    private fun catch(
        error: (Throwable) -> Unit,
        t: Throwable
    ) {
        onStatusChanged(ViewStatus.FAIL)
        error(t)   //有基类自行处理,业务层也可以自行处理
    }

    /**
     * 最终执行
     */
    private fun end(isShowDialog: Boolean, complete: () -> Unit) {
        if (isShowDialog) {
            viewModel.uiObserver.dismissDialog.call()
        }
        complete()
    }

    /**
     * 请求状态切换
     */
    fun onStatusChanged(status: String) {
        viewModel.uiObserver.viewStatus.postValue(status)
    }

    /**
     * token失效通知
     */
    private fun notifyTokenExpired() {
        "token 过期".toast()
//        FEvent.get<Boolean>(GlobalEvent.GLOBAL_TOKEN_EXPIRED.name).post(true)
    }


}