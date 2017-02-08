package com.bihe0832.shakeba.framework.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.Toast;

import com.bihe0832.shakeba.R;
import com.bihe0832.shakeba.framework.Shakeba;
import com.bihe0832.shakeba.framework.activity.base.BaseActivity;
import com.bihe0832.shakeba.framework.fragment.MainFragment;
import com.bihe0832.shakeba.framework.fragment.base.WebViewFragment;
import com.bihe0832.shakeba.framework.common.eGAME;
import com.bihe0832.shakeba.libware.device.ShakeListener;
import com.bihe0832.shakeba.libware.file.Logger;
import com.bihe0832.shakeba.libware.ui.ToastUtil;
import com.bihe0832.shakeba.libware.ui.ViewUtils;
import com.bihe0832.shakeba.libware.util.TextUtils;
import com.bihe0832.shakeba.module.about.AboutAuthorFragment;
import com.bihe0832.shakeba.module.about.AboutShakeBaFragment;
import com.bihe0832.shakeba.module.about.FeedbackFragment;
import com.bihe0832.shakeba.module.about.HelpFragment;
import com.bihe0832.shakeba.module.game.GameAdapter;
import com.bihe0832.shakeba.module.game.GameBaseFragment;
import com.bihe0832.shakeba.module.game.adventure.AdventureFragment;
import com.bihe0832.shakeba.module.game.dice.DiceFragment;
import com.bihe0832.shakeba.module.game.exam.ExamFragment;
import com.bihe0832.shakeba.module.update.ShakebaUpdate;

public class MainActivity extends BaseActivity {

    private DrawerLayout mDrawerLayout;//侧边菜单视图
    private ActionBarDrawerToggle mDrawerToggle;  //菜单开关
    private Toolbar mToolbar;
    private NavigationView mNavigationView;//侧边菜单项

    private FragmentManager mFragmentManager;
    private Fragment mCurrentFragment;

    private String mLastGameFragmentName;
    private Class<?> mLastGameFragmentCls;

    // 摇一摇监控的listener
    private ShakeListener mShakeListener = null;
    // 震动控制器
    private Vibrator mVibrator;
    // 摇晃中两次触发传送摇一摇事件的时间间隔
    private static final int SHAKE_TIME_LIMIT = 800;
    private long lastShakeTime = 0;

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Logger.d(intent);
        handleIntent(intent);
    }

    @Override
    protected int getLayoutResourceID() {
        return R.layout.com_bihe0832_shakeba_main_activity;
    }

    @Override
    protected void init() {
        mFragmentManager = getSupportFragmentManager();
    }

    //切换Fragment
    private void switchFragment(String titleName, Class<?> clazz) {
        if(TextUtils.ckIsEmpty(titleName)){
            mToolbar.setTitle(R.string.app_name);
        }else{
            mToolbar.setTitle(titleName);
        }
        Fragment to = ViewUtils.createFrgment(clazz);
        if (to.isAdded()) {
            mFragmentManager.beginTransaction().hide(mCurrentFragment).show(to).commit();
        } else {
            mFragmentManager.beginTransaction().hide(mCurrentFragment).add(R.id.frame_content, to).commit();
        }
        mCurrentFragment = to;
    }

    private void showMenu(int id){
        mNavigationView.getMenu().getItem(getResources().getInteger(id)).setVisible(true);
    }

    private void hideMenu(int id){
        mNavigationView.getMenu().getItem(getResources().getInteger(id)).setVisible(false);
    }

    //初始化默认选中的Fragment
    private void initDefaultFragment() {
        mCurrentFragment = ViewUtils.createFrgment(MainFragment.class);
        mFragmentManager.beginTransaction().add(R.id.frame_content, mCurrentFragment).commit();
    }

    private void setNavigationViewItemClickListener() {
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_item_home:
                        switchFragment(getString(R.string.app_name),MainFragment.class);
                        quitGame();
                        break;
                    case R.id.navigation_item_game:
                        switchFragment(mLastGameFragmentName,mLastGameFragmentCls);
                        enterGame();
                        break;
                    case R.id.navigation_item_feedback:
                        switchFragment(getString(R.string.menu_key_feedback),FeedbackFragment.class);
                        quitGame();
                        break;
                    case R.id.navigation_item_help:
                        switchFragment(getString(R.string.menu_key_help),HelpFragment.class);
                        quitGame();
                        break;
                    case R.id.navigation_item_shakeba:
                        switchFragment(getString(R.string.menu_key_shakeba),AboutShakeBaFragment.class);
                        quitGame();
                        break;
                    case R.id.navigation_item_me:
                        switchFragment(getString(R.string.menu_key_me),AboutAuthorFragment.class);
                        quitGame();
                        break;
                    case R.id.navigation_item_share:
                        shareToFriend(MainActivity.this);
                        break;
                    case R.id.navigation_item_update:
                        ShakebaUpdate.getInstance().checkUpdate(MainActivity.this,false);
                        quitGame();
                        break;
                    default:
                        break;
                }
                item.setChecked(true);
                mDrawerLayout.closeDrawer(Gravity.LEFT);
                return false;
            }
        });
    }

    @Override
    protected void initView() {
        mToolbar = customFindViewById(R.id.toolbar);
        mDrawerLayout = customFindViewById(R.id.drawer_layout);
        mNavigationView = customFindViewById(R.id.navigation_view);

        mToolbar.setTitle(R.string.app_name);
        //这句一定要在下面几句之前调用，不然就会出现点击无反应
        setSupportActionBar(mToolbar);
        //ActionBarDrawerToggle配合Toolbar，实现Toolbar上菜单按钮开关效果。
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close);
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        setNavigationViewItemClickListener();
        initDefaultFragment();
        ShakebaUpdate.getInstance().checkUpdate(this,true);

        mShakeListener = new ShakeListener(Shakeba.getInstance().getApplicationContext());
        mVibrator = (Vibrator) Shakeba.getInstance().getApplicationContext().getSystemService(
                Context.VIBRATOR_SERVICE);

        mShakeListener.setOnShakeListener(new ShakeListener.OnShakeListener() {
            public void onShake() {
                long currentUpdateTime = System.currentTimeMillis();
                long timeWaitInterval = currentUpdateTime - lastShakeTime;
                if (timeWaitInterval > SHAKE_TIME_LIMIT) {
                    //只有正在游戏页面的时候才开启效果
                    if(Shakeba.getInstance().isGameStarting() && mCurrentFragment instanceof GameBaseFragment){
                        startGame();
                        lastShakeTime = System.currentTimeMillis();
                    }else{
                        Logger.d("game is paused");
                    }
                }
            }
        });
    }

    public void onResume() {
        super.onResume();
        if (mShakeListener != null) {
            mShakeListener.start();
        }
    }

    public void onPause() {
        super.onPause();
        if (mShakeListener != null) {
            mShakeListener.stop();
        }
    }

    // 定义震动
    protected void startVibrato() {
        // 第一个｛｝里面是节奏数组，
        // 第二个参数是重复次数，-1为不重复，非-1从pattern的指定下标开始重复
        mVibrator.vibrate(new long[]{50, 200, 100, 300}, -1);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
            mDrawerLayout.closeDrawer(Gravity.LEFT);
            return;
        }

        if (mCurrentFragment instanceof WebViewFragment) {//如果当前的Fragment是WebViewFragment 则监听返回事件
            WebViewFragment webViewFragment = (WebViewFragment) mCurrentFragment;
            if (webViewFragment.canGoBack()) {
                webViewFragment.goBack();
                return;
            }
        }
        exitGame();
    }

    private long lastBackKeyDownTick = 0;
    public static final long MAX_DOUBLE_BACK_DURATION = 1500;
    private void exitGame(){
        long currentTick = System.currentTimeMillis();
        if (currentTick - lastBackKeyDownTick > MAX_DOUBLE_BACK_DURATION) {
            Snackbar.make(mDrawerLayout, "再按一次退出", Snackbar.LENGTH_SHORT).show();
            lastBackKeyDownTick = currentTick;
        } else {
            finish();
        }
    }

    private void shareToFriend(final Activity activity){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_text));
        sendIntent.setType("text/plain");
        activity.startActivity(Intent.createChooser(sendIntent, getString(R.string.share_title)));
    }


    private void handleIntent(Intent intent){
        if(null != intent && null != intent.getExtras()){
            if(intent.getExtras().containsKey(GameAdapter.INTENT_EXTRA_KEY_GAMEID)){
                int fragment = intent.getExtras().getInt(GameAdapter.INTENT_EXTRA_KEY_GAMEID);
                String gameName = intent.getExtras().getString(GameAdapter.INTENT_EXTRA_KEY_GAMENAME);
                mLastGameFragmentName = getString(R.string.app_name) + "之" + gameName;
                if(eGAME.DICE.val() == fragment){
                    switchFragment(mLastGameFragmentName,DiceFragment.class);
                    mLastGameFragmentCls = DiceFragment.class;
                    enterGame();
                }else if(eGAME.ADVENTURE.val() == fragment){
                    switchFragment(mLastGameFragmentName,AdventureFragment.class);
                    mLastGameFragmentCls = AdventureFragment.class;
                    enterGame();
                }else if(eGAME.EXAM.val() == fragment){
                    switchFragment(mLastGameFragmentName,ExamFragment.class);
                    mLastGameFragmentCls = ExamFragment.class;
                    enterGame();
                }else{
                    ToastUtil.show(this, String.format( getString(R.string.game_start_game_not_found),gameName),Toast.LENGTH_LONG);
                }
            }
        }
    }

    private void startGame(){
        startVibrato();
        if(mCurrentFragment instanceof GameBaseFragment){
            ((GameBaseFragment) mCurrentFragment).startGame();
        }
    }

    private void enterGame(){
        hideMenu(R.integer.navigation_item_game_id);
        Shakeba.getInstance().startGame();
    }

    private void quitGame(){
        showMenu(R.integer.navigation_item_game_id);
        Shakeba.getInstance().pauseGame();
    }
}
