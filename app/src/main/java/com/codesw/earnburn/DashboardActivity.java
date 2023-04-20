package com.codesw.earnburn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.*;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.appbar.AppBarLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import android.widget.LinearLayout;
import android.app.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.content.*;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.webkit.*;
import android.animation.*;
import android.view.animation.*;
import java.util.*;
import java.util.regex.*;
import java.text.*;
import org.json.*;
import java.util.HashMap;
import java.util.ArrayList;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.ImageView;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import android.content.Intent;
import android.net.Uri;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import android.app.Activity;
import android.content.SharedPreferences;
import java.text.DecimalFormat;
import android.graphics.Typeface;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;


public class DashboardActivity extends  AppCompatActivity  { 
	
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private Toolbar _toolbar;
	private AppBarLayout _app_bar;
	private CoordinatorLayout _coordinator;
	private DrawerLayout _drawer;
	private HashMap<String, Object> map = new HashMap<>();
	private String resId = "";
	private String points = "";
	private String validity = "";
	private String packaged = "";
	private String lastChecked = "";
	private String daily = "";
	private boolean hasPackage = false;
	private String res = "";
	private HashMap<String, Object> mapvar = new HashMap<>();
	private HashMap<String, Object> random = new HashMap<>();
	private double num = 0;
	private HashMap<String, Object> temp = new HashMap<>();
	private HashMap<String, Object> wCome = new HashMap<>();
	
	private ArrayList<HashMap<String, Object>> list = new ArrayList<>();
	
	private LinearLayout linear1;
	private ListView listview1;
	private LinearLayout linear3;
	private LinearLayout linear2;
	private LinearLayout linear4;
	private TextView textview1;
	private TextView textview2;
	private LinearLayout _drawer_linear2;
	private LinearLayout _drawer_linear1;
	private ImageView _drawer_imageview1;
	private LinearLayout _drawer_linear3;
	private TextView _drawer_textview1;
	private TextView _drawer_textview2;
	
	private DatabaseReference Membership = _firebase.getReference("membership");
	private ChildEventListener _Membership_child_listener;
	private DatabaseReference userdb = _firebase.getReference("userdb");
	private ChildEventListener _userdb_child_listener;
	private FirebaseAuth fAuth;
	private OnCompleteListener<Void> fAuth_updateEmailListener;
	private OnCompleteListener<Void> fAuth_updatePasswordListener;
	private OnCompleteListener<Void> fAuth_emailVerificationSentListener;
	private OnCompleteListener<Void> fAuth_deleteUserListener;
	private OnCompleteListener<Void> fAuth_updateProfileListener;
	private OnCompleteListener<AuthResult> fAuth_phoneAuthListener;
	private OnCompleteListener<AuthResult> fAuth_googleSignInListener;
	private OnCompleteListener<AuthResult> _fAuth_create_user_listener;
	private OnCompleteListener<AuthResult> _fAuth_sign_in_listener;
	private OnCompleteListener<Void> _fAuth_reset_password_listener;
	private Intent i = new Intent();
	private Calendar cNow = Calendar.getInstance();
	private Calendar cLastCheck = Calendar.getInstance();
	private Calendar cValidity = Calendar.getInstance();
	private SharedPreferences sp;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.dashboard);
		initialize(_savedInstanceState);
		com.google.firebase.FirebaseApp.initializeApp(this);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		
		_app_bar = (AppBarLayout) findViewById(R.id._app_bar);
		_coordinator = (CoordinatorLayout) findViewById(R.id._coordinator);
		_toolbar = (Toolbar) findViewById(R.id._toolbar);
		setSupportActionBar(_toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _v) {
				onBackPressed();
			}
		});
		_drawer = (DrawerLayout) findViewById(R.id._drawer);
		ActionBarDrawerToggle _toggle = new ActionBarDrawerToggle(DashboardActivity.this, _drawer, _toolbar, R.string.app_name, R.string.app_name);
		_drawer.addDrawerListener(_toggle);
		_toggle.syncState();
		
		LinearLayout _nav_view = (LinearLayout) findViewById(R.id._nav_view);
		
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		listview1 = (ListView) findViewById(R.id.listview1);
		linear3 = (LinearLayout) findViewById(R.id.linear3);
		linear2 = (LinearLayout) findViewById(R.id.linear2);
		linear4 = (LinearLayout) findViewById(R.id.linear4);
		textview1 = (TextView) findViewById(R.id.textview1);
		textview2 = (TextView) findViewById(R.id.textview2);
		_drawer_linear2 = (LinearLayout) _nav_view.findViewById(R.id.linear2);
		_drawer_linear1 = (LinearLayout) _nav_view.findViewById(R.id.linear1);
		_drawer_imageview1 = (ImageView) _nav_view.findViewById(R.id.imageview1);
		_drawer_linear3 = (LinearLayout) _nav_view.findViewById(R.id.linear3);
		_drawer_textview1 = (TextView) _nav_view.findViewById(R.id.textview1);
		_drawer_textview2 = (TextView) _nav_view.findViewById(R.id.textview2);
		fAuth = FirebaseAuth.getInstance();
		sp = getSharedPreferences("sp", Activity.MODE_PRIVATE);
		
		_Membership_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if (_childKey.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
					validity = _childValue.get("validity").toString();
					try
							{
									
									Date date1 = new SimpleDateFormat("dd/MM/yy hh:mm:ss").parse(validity);
									cValidity.setTime(date1);
							}
							catch (java.text.ParseException execption)
							{}
					if (_childValue.get("plan").toString().equals("true")) {
						SketchwareUtil.showMessage(getApplicationContext(), "has plan");
						daily = _childValue.get("daily").toString();
						lastChecked = _childValue.get("update").toString();
						if (cValidity.getTimeInMillis() < cNow.getTimeInMillis()) {
							//Check expired
							SketchwareUtil.showMessage(getApplicationContext(), "but expired");
							//add last Time valid money
							hasPackage = false;
							if (!lastChecked.contains(validity.substring((int)(0), (int)(validity.lastIndexOf("/") + 3)))) {
								try
										{
												Date date3 =new SimpleDateFormat("dd/MM/yy hh:mm:ss").parse(lastChecked);
									cLastCheck.setTime(date3);
												
										}
										catch (java.text.ParseException execption)
										{}
								num = daysBetween(cLastCheck, cValidity);
								if (0 < num) {
									SketchwareUtil.showMessage(getApplicationContext(), "Adding existing points ".concat(String.valueOf((long)(num))));
									mapvar = new HashMap<>();
									mapvar.put("update", new SimpleDateFormat("dd/MM/yy hh:mm:ss").format(cValidity.getTime()));
									Membership.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(mapvar);
									
									mapvar = new HashMap<>();
									mapvar.put("points", String.valueOf(Double.parseDouble(points) + (Double.parseDouble(daily) * num)));
									userdb.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(mapvar);
									
								}
							}
							mapvar = new HashMap<>();
							mapvar.put("plan", "false");
							mapvar.put("status", "expired");
							Membership.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(mapvar);
						}
						else {
							hasPackage = true;
						}
						if (hasPackage) {
							try
									{
											Date date2 =new SimpleDateFormat("dd/MM/yy hh:mm:ss").parse(lastChecked);
								cLastCheck.setTime(date2);
											
									}
									catch (java.text.ParseException execption)
									{}
							//Verified and user has package and not expired
							num = daysBetween(cLastCheck, cNow);
							if (0 < num) {
								SketchwareUtil.showMessage(getApplicationContext(), "Adding points ".concat(String.valueOf((long)(num))));
								mapvar = new HashMap<>();
								cLastCheck.add(Calendar.HOUR, (int)(24));
								mapvar.put("update", new SimpleDateFormat("dd/MM/yy hh:mm:ss").format(cLastCheck.getTime()));
								Membership.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(mapvar);
								
								mapvar = new HashMap<>();
								mapvar.put("points", String.valueOf(Double.parseDouble(points) + (Double.parseDouble(daily) * num)));
								userdb.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(mapvar);
								
							}
						}
						else {
							if ((cValidity.getTimeInMillis() + 604800000) < cNow.getTimeInMillis()) {
								//plan false and after 1 week 
								//Show Alert Dialog
								_banned();
								SketchwareUtil.showMessage(getApplicationContext(), "Don't have plans after expireing time");
							}
							else {
								SketchwareUtil.showMessage(getApplicationContext(), "May expire free time");
								_warning();
							}
						}
					}
					else {
						if ((cValidity.getTimeInMillis() + 604800000) < cNow.getTimeInMillis()) {
							//Show Alert Dialog
							SketchwareUtil.showMessage(getApplicationContext(), "Don't have plans from beginning");
							//plan false and after 1 week 
							_banned();
						}
						else {
							if (!sp.getString("welcome", "").equals("")) {
								wCome = new Gson().fromJson(sp.getString("welcome", ""), new TypeToken<HashMap<String, Object>>(){}.getType());
								_welcome();
							}
							else {
								SketchwareUtil.showMessage(getApplicationContext(), "May expire trial soon");
								_warning();
							}
						}
					}
				}
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
			}
			
			@Override
			public void onChildMoved(DataSnapshot _param1, String _param2) {
				
			}
			
			@Override
			public void onChildRemoved(DataSnapshot _param1) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
			}
			
			@Override
			public void onCancelled(DatabaseError _param1) {
				final int _errorCode = _param1.getCode();
				final String _errorMessage = _param1.getMessage();
				
			}
		};
		Membership.addChildEventListener(_Membership_child_listener);
		
		_userdb_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if (_childKey.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
					points = _childValue.get("points").toString();
					textview2.setText(new DecimalFormat("#.####").format(Double.parseDouble(points)).concat(" D-50C"));
					_drawer_textview1.setText(_childValue.get("name").toString());
					_drawer_textview2.setText(_childValue.get("email").toString());
					_curcle_igm_url(_childValue.get("avatar").toString(), _drawer_imageview1);
					sp.edit().putString("trxPw", _childValue.get("trxPw").toString()).commit();
					sp.edit().putString("name", _childValue.get("name").toString()).commit();
					sp.edit().putString("email", _childValue.get("email").toString()).commit();
					sp.edit().putString("points", new DecimalFormat("#.####").format(Double.parseDouble(points)).concat(" D-50C")).commit();
					Membership.addChildEventListener(_Membership_child_listener);
				}
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if (_childKey.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
					points = _childValue.get("points").toString();
					textview2.setText(new DecimalFormat("#.####").format(Double.parseDouble(points)).concat(" D-50C"));
				}
			}
			
			@Override
			public void onChildMoved(DataSnapshot _param1, String _param2) {
				
			}
			
			@Override
			public void onChildRemoved(DataSnapshot _param1) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
			}
			
			@Override
			public void onCancelled(DatabaseError _param1) {
				final int _errorCode = _param1.getCode();
				final String _errorMessage = _param1.getMessage();
				
			}
		};
		userdb.addChildEventListener(_userdb_child_listener);
		
		fAuth_updateEmailListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		fAuth_updatePasswordListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		fAuth_emailVerificationSentListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		fAuth_deleteUserListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		fAuth_phoneAuthListener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> task){
				final boolean _success = task.isSuccessful();
				final String _errorMessage = task.getException() != null ? task.getException().getMessage() : "";
				
			}
		};
		
		fAuth_updateProfileListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		fAuth_googleSignInListener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> task){
				final boolean _success = task.isSuccessful();
				final String _errorMessage = task.getException() != null ? task.getException().getMessage() : "";
				
			}
		};
		
		_fAuth_create_user_listener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		_fAuth_sign_in_listener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		_fAuth_reset_password_listener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				
			}
		};
	}
	
	private void initializeLogic() {
		setTitle("Dashboard");
		textview1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/manrope_bold.ttf"), 0);
		textview2.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/manrope_bold.ttf"), 0);
		_shapeRadius(linear4, "#FAFAFA", 15);
		_shapeRadius(linear3, "#E0F1FD", SketchwareUtil.getDip(getApplicationContext(), (int)(15)));
		Toolbar.LayoutParams params = new Toolbar.LayoutParams(Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.MATCH_PARENT);
		params.gravity = Gravity.CENTER;
		
		params.topMargin = 20;
		                    ((TextView)_toolbar.getChildAt(0)).setLayoutParams(params);
		
		((TextView)_toolbar.getChildAt(0)).setTextSize(18);
		
		((TextView)_toolbar.getChildAt(0)).setGravity(Gravity.CENTER);
		
		((TextView)_toolbar.getChildAt(0)).setTextColor(Color.parseColor("#000000"));
		
		((TextView)_toolbar.getChildAt(0)).setTypeface(Typeface.createFromAsset(getAssets(),"fonts/manrope_bold.ttf"), 0);
		
		((ImageView)_toolbar.getChildAt(1)).setImageResource(R.drawable.ic_menu_black);
			com.google.android.material.appbar.AppBarLayout appBarLayout = (com.google.android.material.appbar.AppBarLayout) _toolbar.getParent();
				appBarLayout.setStateListAnimator(null);
		GridView grid1 = new GridView(DashboardActivity.this);
		grid1.setId(2);
		linear2.addView(grid1);
		grid1.setAdapter(new Listview1Adapter(list));
		grid1.setNumColumns(3); 
		grid1.setColumnWidth(GridView.AUTO_FIT); 
		grid1.setStretchMode(GridView.STRETCH_COLUMN_WIDTH); 
		grid1.setVerticalScrollBarEnabled(false); 
		_additem();
		_refresh();
		_setDrawerWidth(255);
		com.google.android.material.navigation.NavigationView nav= new com.google.android.material.navigation.NavigationView(DashboardActivity.this);
		nav.setLayoutParams(new ViewGroup.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
		
		//View inflate = getLayoutInflater().inflate(R.layout.header, null);
		
		//nav.addHeaderView(inflate);
		
		nav.setItemTextColor(android.content.res.ColorStateList.valueOf(Color.parseColor("#2196F3")));
		
		nav.setItemIconTintList(android.content.res.ColorStateList.valueOf(Color.parseColor("#2196F3")));
		
		//nav.setItemBackgroundResource(R.drawable.ic_1);
		_drawer_linear1.addView(nav);
		
		Menu menu = nav.getMenu();
		getMenuInflater().inflate(R.menu.drawer, menu);
		
		/*final int A = 1;
final int B = 2;
final int C = 3;
  
menu.add(Menu.NONE, A, Menu.NONE, "Home").setIcon(R.drawable.
ic_1);

menu.add(Menu.NONE, B, Menu.NONE, "Downloading").setIcon(R.drawable.
ic_2);

menu.add(Menu.NONE, C, Menu.NONE, "Downloaded").setIcon(R.drawable.
ic_3);
*/
		/*final Menu navMenu = nav.getMenu();
        nav.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ArrayList<View> menuItems = new ArrayList<>(); 
                nav.getViewTreeObserver().removeOnGlobalLayoutListener(this); 
                for (int i = 0; i < navMenu.size(); i++) {

                    final MenuItem item = navMenu.getItem(i);
                    nav.findViewsWithText(menuItems, item.getTitle(), View.FIND_VIEWS_WITH_TEXT);
                }
                for (final View menuItem : menuItems) {

                    ((TextView) menuItem).setTypeface(Typeface.createFromAsset(getAssets(),"fonts/gbold.ttf"), 0);
                }
            }
        });*/
		nav.setNavigationItemSelectedListener(new com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener() {
						@Override
						public boolean onNavigationItemSelected(MenuItem item) {
				
								final int _itemId = item.getItemId();
				if(isConnected()){
									if (_itemId == R.id.nav_1 ) {
										_drawer(false);
											
											i.setClass(getApplicationContext(), MembershipActivity.class);
														startActivity(i);
									}
									if (_itemId == R.id.nav_2) {
										_drawer(false);
											i.setClass(getApplicationContext(), PeerToPeerActivity.class);
														startActivity(i);
									}
									if (_itemId == R.id.nav_3) {
										    _drawer(false);
											i.setClass(getApplicationContext(), WithdrawActivity.class);
														startActivity(i);
									}
					if (_itemId == R.id.nav_4) {
										_drawer(false);
											i.setClass(getApplicationContext(), ReferralsActivity.class);
														startActivity(i);
									}
									if (_itemId == R.id.nav_5) {
										    _drawer(false);
											i.setClass(getApplicationContext(), FeedbackActivity.class);
														startActivity(i);
									}
					if (_itemId == R.id.nav_6) {
										    _drawer(false);
						FirebaseAuth.getInstance().signOut();
						sp.edit().remove("name").commit();
						sp.edit().remove("email").commit();
						sp.edit().remove("points").commit();
						sp.edit().remove("trxPw").commit();
						Membership.removeEventListener(_Membership_child_listener);
						userdb.removeEventListener(_userdb_child_listener);
						i.setClass(getApplicationContext(), AuthActivity.class);
														startActivity(i);
						finish();
											
									}
				} else {
					showMessage("No internet");
				}
								return true;
						}
				});
		if (!sp.getString("name", "").equals("")) {
			_drawer_textview1.setText(sp.getString("name", ""));
		}
		if (!sp.getString("email", "").equals("")) {
			_drawer_textview2.setText(sp.getString("email", ""));
		}
		if (!sp.getString("points", "").equals("")) {
			textview2.setText(sp.getString("points", ""));
		}
		Membership.removeEventListener(_Membership_child_listener);
		if (!isConnected()) {
			SketchwareUtil.showMessage(getApplicationContext(), "No internet, data isn't synced");
		}
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			
			default:
			break;
		}
	}
	
	@Override
	public void onBackPressed() {
		if (_drawer.isDrawerOpen(GravityCompat.START)) {
			_drawer.closeDrawer(GravityCompat.START);
		}
		else {
			super.onBackPressed();
		}
	}
	public void _additem () {
		map = new HashMap<>();
		map.put("title", "Buy Pack");
		map.put("resId", "wallet");
		map.put("value", "100");
		list.add(map);
		map = new HashMap<>();
		map.put("title", "Topup");
		map.put("resId", "topup");
		map.put("value", "200");
		list.add(map);
		map = new HashMap<>();
		map.put("title", "Withdraw");
		map.put("resId", "buy_pack");
		map.put("value", "300");
		list.add(map);
		map = new HashMap<>();
		map.put("title", "Invite");
		map.put("resId", "invite_2");
		map.put("value", "300");
		list.add(map);
		map = new HashMap<>();
		map.put("title", "Feedback");
		map.put("resId", "feedback");
		map.put("value", "300");
		list.add(map);
		map = new HashMap<>();
		map.put("title", "Post");
		map.put("resId", "post");
		map.put("value", "300");
		list.add(map);
	}
	
	
	public void _refresh () {
		GridView grid = (GridView)findViewById(2);
		grid.setAdapter(new Listview1Adapter(list));
		((BaseAdapter)grid.getAdapter()).notifyDataSetChanged();
	}
	
	
	public void _shapeRadius (final View _v, final String _color, final double _radius) {
		android.graphics.drawable.GradientDrawable shape = new android.graphics.drawable.GradientDrawable();
		  shape.setShape(android.graphics.drawable.GradientDrawable.RECTANGLE);
		
		shape.setCornerRadius((int)_radius);
		
		shape.setColor(Color.parseColor(_color));
		_v.setBackgroundDrawable(shape);
	}
	
	
	public void _radius_to (final View _view, final double _radius, final double _shadow, final String _color) {
		android.graphics.drawable.GradientDrawable ab = new android.graphics.drawable.GradientDrawable();
		
		ab.setColor(Color.parseColor(_color));
		ab.setCornerRadius((float) _radius);
		_view.setElevation((float) _shadow);
		_view.setBackground(ab);
	}
	
	
	public void _drawer (final boolean _action) {
		if (_action) {
			_drawer.openDrawer(GravityCompat.START);
		}
		else {
			if (!_action) {
				_drawer.closeDrawer(GravityCompat.START);
			}
		}
	}
	
	
	public void _setDrawerWidth (final double _num) {
		LinearLayout _nav_view = (LinearLayout) findViewById(R.id._nav_view);
		
		_nav_view.setBackgroundColor(Color.parseColor("#FFFFFF"));
		
		androidx.drawerlayout.widget.DrawerLayout.LayoutParams params = (androidx.drawerlayout.widget.DrawerLayout.LayoutParams)_nav_view.getLayoutParams();
		
		params.width = (int)getDip((int)_num);
		
		params.height = androidx.drawerlayout.widget.DrawerLayout.LayoutParams.MATCH_PARENT;
		
		_nav_view.setLayoutParams(params);
	}
	
	
	public void _warning () {
		if (!isFinishing()) {
			
			final AlertDialog dialog = new AlertDialog.Builder(DashboardActivity.this).create();
			LayoutInflater inflater = getLayoutInflater();
			
			View convertView = (View) inflater.inflate(R.layout.account, null);
			dialog.setView(convertView);
			dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
			TextView b1 = (TextView)
			convertView.findViewById(R.id.b1);
			TextView b2 = (TextView)
			convertView.findViewById(R.id.b2);
			TextView t1 = (TextView)
			convertView.findViewById(R.id.t1);
			TextView t2 = (TextView)
			convertView.findViewById(R.id.t2);
			LinearLayout gr = (LinearLayout)
			convertView.findViewById(R.id.gr);
			
			LinearLayout indicator = (LinearLayout)
			convertView.findViewById(R.id.indicator);
			
			indicator.setBackgroundColor(Color.parseColor("#ffcc00"));
			
			ImageView i1 = (ImageView)
			convertView.findViewById(R.id.i1);
			t1.setText("Warning");
			t2.setText("It's seemed like you don't have any package in last 7 days, try to add a new package !");
			b1.setText("Dismiss");
			b2.setText("Buy Pack");
			_rippleRoundStroke(gr, "#FFFFFF", "#FFFFFF", 15, 0, "#000000");
			_rippleRoundStroke(b1, "#FFFFFF", "#EEEEEE", 15, 2.5d, "#EEEEEE");
			_rippleRoundStroke(b2, "#2196F3", "#EEEEEE", 15, 0, "#EEEEEE");
			_RoundAndBorder(i1, "#ffcc00", 0, "#ffcc00", 100);
			i1.setElevation((float)0.1d);
			_ICC(i1, "#FFFFFF", "#FFFFFF");
			b1.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					
					dialog.dismiss();
				}
			});
			b2.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					
					i.setClass(getApplicationContext(), MembershipActivity.class);
					startActivity(i);
					dialog.dismiss();
				}
			});
			dialog.show();
		}
	}
	
	
	public void _RoundAndBorder (final View _view, final String _color1, final double _border, final String _color2, final double _round) {
		android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
		gd.setColor(Color.parseColor(_color1));
		gd.setCornerRadius((int) _round);
		gd.setStroke((int) _border, Color.parseColor(_color2));
		_view.setBackground(gd);
	}
	
	
	public void _ICC (final ImageView _img, final String _c1, final String _c2) {
		_img.setImageTintList(new android.content.res.ColorStateList(new int[][] {{-android.R.attr.state_pressed},{android.R.attr.state_pressed}},new int[]{Color.parseColor(_c1), Color.parseColor(_c2)}));
	}
	
	
	public void _rippleRoundStroke (final View _view, final String _focus, final String _pressed, final double _round, final double _stroke, final String _strokeclr) {
		android.graphics.drawable.GradientDrawable GG = new android.graphics.drawable.GradientDrawable();
		GG.setColor(Color.parseColor(_focus));
		GG.setCornerRadius((float)_round);
		GG.setStroke((int) _stroke,
		Color.parseColor("#" + _strokeclr.replace("#", "")));
		android.graphics.drawable.RippleDrawable RE = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ Color.parseColor(_pressed)}), GG, null);
		_view.setClickable(true);
		_view.setBackground(RE);
	}
	
	
	public void _banned () {
		if (!isFinishing()) {
			
			final AlertDialog dialog = new AlertDialog.Builder(DashboardActivity.this).create();
			LayoutInflater inflater = getLayoutInflater();
			
			View convertView = (View) inflater.inflate(R.layout.account, null);
			dialog.setView(convertView);
			dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
			TextView b1 = (TextView)
			convertView.findViewById(R.id.b1);
			TextView b2 = (TextView)
			convertView.findViewById(R.id.b2);
			TextView t1 = (TextView)
			convertView.findViewById(R.id.t1);
			TextView t2 = (TextView)
			convertView.findViewById(R.id.t2);
			LinearLayout gr = (LinearLayout)
			convertView.findViewById(R.id.gr);
			
			ImageView i1 = (ImageView)
			convertView.findViewById(R.id.i1);
			_rippleRoundStroke(gr, "#FFFFFF", "#FFFFFF", 15, 0, "#000000");
			_rippleRoundStroke(b1, "#FFFFFF", "#EEEEEE", 15, 2.5d, "#EEEEEE");
			_rippleRoundStroke(b2, "#2196F3", "#EEEEEE", 15, 0, "#EEEEEE");
			_RoundAndBorder(i1, "#D50000", 0, "#D50000", 100);
			i1.setElevation((float)0.1d);
			_ICC(i1, "#FFFFFF", "#FFFFFF");
			b1.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					
					finishAffinity();
					dialog.dismiss();
				}
			});
			b2.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					
					i.setClass(getApplicationContext(), FeedbackActivity.class);
					startActivity(i);
					finish();
					dialog.dismiss();
				}
			});
			dialog.setCancelable(false);
			dialog.show();
		}
	}
	
	
	public void _daysBetween () {
	} public static long daysBetween(Calendar startDate, Calendar endDate) {
				long end = endDate.getTimeInMillis();
				long start = startDate.getTimeInMillis();
				return java.util.concurrent.TimeUnit.MILLISECONDS.toDays(Math.abs(end - start));
	}
	
	
	public void _curcle_igm_url (final String _url, final ImageView _img_view) {
		
		Glide.with(getApplicationContext()).asBitmap().load(_url).centerCrop().into(new com.bumptech.glide.request.target.BitmapImageViewTarget(_img_view) {
			@Override protected void setResource(Bitmap resource) {
				androidx.core.graphics.drawable.RoundedBitmapDrawable circularBitmapDrawable = androidx.core.graphics.drawable.RoundedBitmapDrawableFactory.create(getApplicationContext().getResources(), resource); circularBitmapDrawable.setCircular(true); _img_view.setImageDrawable(circularBitmapDrawable);
			}
		});
	}
	
	
	public void _welcome () {
		if (!isFinishing()) {
			
			final AlertDialog dialog = new AlertDialog.Builder(DashboardActivity.this).create();
			LayoutInflater inflater = getLayoutInflater();
			
			View convertView = (View) inflater.inflate(R.layout.welcome, null);
			dialog.setView(convertView);
			dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
			TextView t1 = (TextView)
			convertView.findViewById(R.id.t1);
			TextView t2 = (TextView)
			convertView.findViewById(R.id.t2);
			TextView t3 = (TextView)
			convertView.findViewById(R.id.t3);
			TextView t4 = (TextView)
			convertView.findViewById(R.id.t4);
			TextView t5 = (TextView)
			convertView.findViewById(R.id.t5);
			TextView t6 = (TextView)
			convertView.findViewById(R.id.t6);
			TextView t7 = (TextView)
			convertView.findViewById(R.id.t7);
			TextView t8 = (TextView)
			convertView.findViewById(R.id.t8);
			LinearLayout gr = (LinearLayout)
			convertView.findViewById(R.id.gr);
			
			TextView b1 = (TextView)
			convertView.findViewById(R.id.b1);
			
			
			ImageView i1 = (ImageView)
			convertView.findViewById(R.id.i1);
			t1.setText(wCome.get("username").toString());
			t2.setText(wCome.get("uid").toString());
			t3.setText(wCome.get("password").toString());
			t4.setText(wCome.get("trxPw").toString());
			t5.setText(wCome.get("email").toString());
			t6.setText(wCome.get("mobile").toString());
			t7.setText(wCome.get("upId").toString());
			t8.setText(wCome.get("upName").toString());
			_rippleRoundStroke(gr, "#FFFFFF", "#FFFFFF", 15, 0, "#000000");
			_rippleRoundStroke(b1, "#FFFFFF", "#EEEEEE", 15, 2.5d, "#EEEEEE");
			b1.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					
					dialog.dismiss();
				}
			});
			dialog.show();
			sp.edit().remove("welcome").commit();
		}
		sp.edit().putString("trxPw", wCome.get("trxPw").toString()).commit();
	}
	
	
	public void _isConnected () {
	} private boolean isConnected() {
				ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		
				return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
	}
	
	
	public class Listview1Adapter extends BaseAdapter {
		ArrayList<HashMap<String, Object>> _data;
		public Listview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public int getCount() {
			return _data.size();
		}
		
		@Override
		public HashMap<String, Object> getItem(int _index) {
			return _data.get(_index);
		}
		
		@Override
		public long getItemId(int _index) {
			return _index;
		}
		@Override
		public View getView(final int _position, View _v, ViewGroup _container) {
			LayoutInflater _inflater = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View _view = _v;
			if (_view == null) {
				_view = _inflater.inflate(R.layout.grid, null);
			}
			
			final LinearLayout linear1 = (LinearLayout) _view.findViewById(R.id.linear1);
			final LinearLayout linear2 = (LinearLayout) _view.findViewById(R.id.linear2);
			final ImageView imageview2 = (ImageView) _view.findViewById(R.id.imageview2);
			final TextView textview3 = (TextView) _view.findViewById(R.id.textview3);
			
			_shapeRadius(linear2, "#FAFAFA", 15);
			_shapeRadius(linear1, "#E0F1FD", SketchwareUtil.getDip(getApplicationContext(), (int)(15)));
			res = list.get((int)_position).get("resId").toString();
			int resId = getApplicationContext().getResources().getIdentifier(res, "drawable", getApplicationContext().getPackageName());
			
			imageview2.setImageResource(resId);
			textview3.setText(list.get((int)_position).get("title").toString());
			textview3.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/manrope_bold.ttf"), 0);
			linear1.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					if (isConnected()) {
						if (_position == 0) {
							i.setClass(getApplicationContext(), MembershipActivity.class);
							startActivity(i);
						}
						else {
							if (_position == 1) {
								i.setClass(getApplicationContext(), TopupActivity.class);
								startActivity(i);
							}
							else {
								if (_position == 2) {
									i.setClass(getApplicationContext(), WithdrawActivity.class);
									startActivity(i);
								}
								else {
									if (_position == 3) {
										i.setClass(getApplicationContext(), ReferralsActivity.class);
										startActivity(i);
									}
									else {
										if (_position == 4) {
											i.setClass(getApplicationContext(), FeedbackActivity.class);
											startActivity(i);
										}
										else {
											i.setClass(getApplicationContext(), PostActivity.class);
											startActivity(i);
										}
									}
								}
							}
						}
					}
					else {
						SketchwareUtil.showMessage(getApplicationContext(), "No internet");
					}
				}
			});
			
			return _view;
		}
	}
	
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}
	
	@Deprecated
	public int getLocationX(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[0];
	}
	
	@Deprecated
	public int getLocationY(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[1];
	}
	
	@Deprecated
	public int getRandom(int _min, int _max) {
		Random random = new Random();
		return random.nextInt(_max - _min + 1) + _min;
	}
	
	@Deprecated
	public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
		ArrayList<Double> _result = new ArrayList<Double>();
		SparseBooleanArray _arr = _list.getCheckedItemPositions();
		for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
			if (_arr.valueAt(_iIdx))
			_result.add((double)_arr.keyAt(_iIdx));
		}
		return _result;
	}
	
	@Deprecated
	public float getDip(int _input){
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
	}
	
	@Deprecated
	public int getDisplayWidthPixels(){
		return getResources().getDisplayMetrics().widthPixels;
	}
	
	@Deprecated
	public int getDisplayHeightPixels(){
		return getResources().getDisplayMetrics().heightPixels;
	}
	
}