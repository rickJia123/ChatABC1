package river.chat.lib_core.router.plugin.module;

import android.net.Uri;
import android.os.Bundle;

import com.alibaba.android.arouter.launcher.ARouter;

import androidx.appcompat.app.AppCompatActivity;
import river.chat.lib_core.utils.longan.ToastKt;

public class SchemeFilterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Uri uri = getIntent().getData();
        ToastKt.toast(uri.toString());
        ARouter.getInstance()
                .build(uri)
                .navigation();
        finish();
    }
}