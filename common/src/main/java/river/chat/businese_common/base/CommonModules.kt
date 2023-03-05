package river.chat.businese_common.base

import com.google.gson.Gson
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * Created by beiyongChao on 2023/3/1
 * Description:
 */
val dataBaseModule = module {

    single(named("gson")) {
        Gson()
    }
}