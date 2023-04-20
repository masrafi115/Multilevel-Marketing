package com.codesw.earnburn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.*;
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
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.EditText;
import android.widget.TextView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ChildEventListener;
import android.content.Intent;
import android.net.Uri;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import android.app.Activity;
import android.content.SharedPreferences;
import android.view.View;
import com.google.gson.Gson;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;


public class AuthActivity extends  AppCompatActivity  { 
	
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private String admins = "";
	private String refId = "";
	private boolean isLogin = false;
	private HashMap<String, Object> map = new HashMap<>();
	private HashMap<String, Object> mapvar = new HashMap<>();
	private HashMap<String, Object> mapData = new HashMap<>();
	private HashMap<String, Object> mapA = new HashMap<>();
	private boolean trueUpline = false;
	private String uplineName = "";
	private HashMap<String, Object> wCome = new HashMap<>();
	
	private ArrayList<HashMap<String, Object>> adminUID = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> checkLine = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> welcome = new ArrayList<>();
	
	private LinearLayout linear1;
	private LinearLayout linear21;
	private ScrollView vscroll2;
	private LinearLayout linear2;
	private LinearLayout linear10;
	private LinearLayout linear11;
	private LinearLayout linear13;
	private LinearLayout linear15;
	private LinearLayout linear17;
	private LinearLayout linear19;
	private LinearLayout linear7;
	private LinearLayout linear8;
	private LinearLayout linear9;
	private EditText edittext1;
	private LinearLayout linear12;
	private EditText edittext2;
	private LinearLayout linear14;
	private EditText edittext3;
	private LinearLayout linear16;
	private EditText edittext4;
	private LinearLayout linear18;
	private EditText edittext5;
	private LinearLayout linear20;
	private EditText edittext6;
	private TextView textview1;
	private TextView textview2;
	
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
	private DatabaseReference userdb = _firebase.getReference("userdb");
	private ChildEventListener _userdb_child_listener;
	private Intent i = new Intent();
	private Calendar c = Calendar.getInstance();
	private DatabaseReference Referrals = _firebase.getReference("referrals");
	private ChildEventListener _Referrals_child_listener;
	private DatabaseReference Membership = _firebase.getReference("membership");
	private ChildEventListener _Membership_child_listener;
	private DatabaseReference Downlines = _firebase.getReference("downlines");
	private ChildEventListener _Downlines_child_listener;
	private SharedPreferences sp;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.auth);
		initialize(_savedInstanceState);
		com.google.firebase.FirebaseApp.initializeApp(this);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		linear21 = (LinearLayout) findViewById(R.id.linear21);
		vscroll2 = (ScrollView) findViewById(R.id.vscroll2);
		linear2 = (LinearLayout) findViewById(R.id.linear2);
		linear10 = (LinearLayout) findViewById(R.id.linear10);
		linear11 = (LinearLayout) findViewById(R.id.linear11);
		linear13 = (LinearLayout) findViewById(R.id.linear13);
		linear15 = (LinearLayout) findViewById(R.id.linear15);
		linear17 = (LinearLayout) findViewById(R.id.linear17);
		linear19 = (LinearLayout) findViewById(R.id.linear19);
		linear7 = (LinearLayout) findViewById(R.id.linear7);
		linear8 = (LinearLayout) findViewById(R.id.linear8);
		linear9 = (LinearLayout) findViewById(R.id.linear9);
		edittext1 = (EditText) findViewById(R.id.edittext1);
		linear12 = (LinearLayout) findViewById(R.id.linear12);
		edittext2 = (EditText) findViewById(R.id.edittext2);
		linear14 = (LinearLayout) findViewById(R.id.linear14);
		edittext3 = (EditText) findViewById(R.id.edittext3);
		linear16 = (LinearLayout) findViewById(R.id.linear16);
		edittext4 = (EditText) findViewById(R.id.edittext4);
		linear18 = (LinearLayout) findViewById(R.id.linear18);
		edittext5 = (EditText) findViewById(R.id.edittext5);
		linear20 = (LinearLayout) findViewById(R.id.linear20);
		edittext6 = (EditText) findViewById(R.id.edittext6);
		textview1 = (TextView) findViewById(R.id.textview1);
		textview2 = (TextView) findViewById(R.id.textview2);
		fAuth = FirebaseAuth.getInstance();
		sp = getSharedPreferences("sp", Activity.MODE_PRIVATE);
		
		linear7.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (isLogin) {
					if (edittext2.getText().toString().equals("") || edittext3.getText().toString().equals("")) {
						SketchwareUtil.showMessage(getApplicationContext(), "Please check input");
					}
					else {
						linear7.setEnabled(false);
						linear8.setEnabled(false);
						fAuth.signInWithEmailAndPassword(edittext2.getText().toString(), edittext3.getText().toString()).addOnCompleteListener(AuthActivity.this, _fAuth_sign_in_listener);
					}
				}
				else {
					if ((edittext1.getText().toString().equals("") || edittext2.getText().toString().equals("")) || ((edittext3.getText().toString().equals("") || edittext4.getText().toString().equals("")) || (edittext5.getText().toString().equals("") || edittext6.getText().toString().equals("")))) {
						SketchwareUtil.showMessage(getApplicationContext(), "Please check input");
					}
					else {
						if (edittext3.getText().toString().equals(edittext4.getText().toString())) {
							trueUpline = false;
							userdb.addListenerForSingleValueEvent(new ValueEventListener() {
								@Override
								public void onDataChange(DataSnapshot _dataSnapshot) {
									checkLine = new ArrayList<>();
									try {
										GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
										for (DataSnapshot _data : _dataSnapshot.getChildren()) {
											HashMap<String, Object> _map = _data.getValue(_ind);
											checkLine.add(_map);
										}
									}
									catch (Exception _e) {
										_e.printStackTrace();
									}
									for(HashMap<String,Object> hmap: checkLine){
													for(Object str:hmap.keySet()){
															
											if (hmap.get("uid").equals(edittext6.getText().toString())) {
												uplineName = hmap.get("name").toString();
												trueUpline = true;
											}
										}}
									if (trueUpline) {
										linear7.setEnabled(false);
										linear8.setEnabled(false);
										fAuth.createUserWithEmailAndPassword(edittext2.getText().toString(), edittext3.getText().toString()).addOnCompleteListener(AuthActivity.this, _fAuth_create_user_listener);
									}
									else {
										SketchwareUtil.showMessage(getApplicationContext(), "Upline doesn't exist on server");
									}
								}
								@Override
								public void onCancelled(DatabaseError _databaseError) {
								}
							});
						}
						else {
							SketchwareUtil.showMessage(getApplicationContext(), "Password didn't match");
						}
					}
				}
			}
		});
		
		linear8.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (isLogin) {
					_a_switch(false);
				}
				else {
					_a_switch(true);
				}
			}
		});
		
		_userdb_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
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
		userdb.addChildEventListener(_userdb_child_listener);
		
		_Referrals_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				c = Calendar.getInstance();
				if (_childKey.equals(refId)) {
					map = new HashMap<>();
					map.put("admin1", refId);
					map.put("admin2", _childValue.get("admin1").toString());
					map.put("admin3", _childValue.get("admin2").toString());
					map.put("admin4", _childValue.get("admin3").toString());
					map.put("admin5", _childValue.get("admin4").toString());
					map.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
				}
				mapA = new HashMap<>();
				mapA.put(FirebaseAuth.getInstance().getCurrentUser().getUid(), "true");
				Downlines.child(refId).updateChildren(mapA);
				Referrals.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(map);
				mapvar = new HashMap<>();
				mapvar.put("name", edittext1.getText().toString());
				mapvar.put("email", edittext2.getText().toString());
				mapvar.put("phone", edittext5.getText().toString());
				mapvar.put("trxPw", String.valueOf((long)(SketchwareUtil.getRandom((int)(10000), (int)(99999)))));
				mapvar.put("points", "0.0");
				mapvar.put("avatar", "https://firebasestorage.googleapis.com/v0/b/mlm-dream.appspot.com/o/Profile%2Facc.png?alt=media&token=6e76d63b-5136-4510-b3ca-e51c62f91e9c");
				mapvar.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
				userdb.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(mapvar);
				mapData = new HashMap<>();
				mapData.put("plan", "false");
				mapData.put("validity", new SimpleDateFormat("dd/MM/yy hh:mm:ss").format(c.getTime()));
				Membership.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(mapData);
				wCome = new HashMap<>();
				wCome.put("username", edittext1.getText().toString());
				wCome.put("email", FirebaseAuth.getInstance().getCurrentUser().getEmail());
				wCome.put("password", edittext3.getText().toString());
				wCome.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
				wCome.put("mobile", edittext5.getText().toString());
				wCome.put("trxPw", mapvar.get("trxPw").toString());
				wCome.put("upId", edittext6.getText().toString());
				wCome.put("upName", uplineName);
				sp.edit().putString("welcome", new Gson().toJson(wCome)).commit();
				fAuth.signInWithEmailAndPassword(edittext2.getText().toString(), edittext3.getText().toString()).addOnCompleteListener(AuthActivity.this, _fAuth_sign_in_listener);
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
		Referrals.addChildEventListener(_Referrals_child_listener);
		
		_Membership_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
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
		
		_Downlines_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
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
		Downlines.addChildEventListener(_Downlines_child_listener);
		
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
				if (_success) {
					c = Calendar.getInstance();
					refId = edittext6.getText().toString();
					Referrals.addChildEventListener(_Referrals_child_listener);
				}
				else {
					linear7.setEnabled(true);
					linear8.setEnabled(true);
					SketchwareUtil.showMessage(getApplicationContext(), _errorMessage);
				}
			}
		};
		
		_fAuth_sign_in_listener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				if (_success) {
					i.setClass(getApplicationContext(), DashboardActivity.class);
					startActivity(i);
					finish();
				}
				else {
					linear7.setEnabled(true);
					linear8.setEnabled(true);
					SketchwareUtil.showMessage(getApplicationContext(), _errorMessage);
				}
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
		Referrals.removeEventListener(_Referrals_child_listener);
		_shadow();
		_shapeRadius(linear2, "#FAFAFA", 10);
		_shapeRadius(linear7, "#2196F3", 10);
		_shapeRadius(linear8, "#2196F3", 10);
		/*
_SetCompoundDrawable(edittext1, 0, "", 0);
_SetCompoundDrawable(edittext2, 0, "", 0);
_SetCompoundDrawable(edittext3, 0, "", 0);
_SetCompoundDrawable(edittext4, 0, "", 0);
_SetCompoundDrawable(edittext5, 0, "", 0);
_SetCompoundDrawable(edittext6, 0, "", 0);
_setCompoundColor(edittext1, "#2196F3");
_setCompoundColor(edittext2, "#2196F3");
_setCompoundColor(edittext3, "#2196F3");
_setCompoundColor(edittext4, "#2196F3");
_setCompoundColor(edittext5, "#2196F3");
_setCompoundColor(edittext6, "#2196F3");
*/
		_a_switch(true);
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			
			default:
			break;
		}
	}
	
	public void _a_switch (final boolean _login) {
		if (_login) {
			isLogin = true;
			linear10.setVisibility(View.GONE);
			linear15.setVisibility(View.GONE);
			linear17.setVisibility(View.GONE);
			linear19.setVisibility(View.GONE);
			textview1.setText("Login");
			textview2.setText("Create Account");
		}
		else {
			isLogin = false;
			linear10.setVisibility(View.VISIBLE);
			linear15.setVisibility(View.VISIBLE);
			linear17.setVisibility(View.VISIBLE);
			linear19.setVisibility(View.VISIBLE);
			textview1.setText("Signup");
			textview2.setText("Login");
		}
	}
	
	
	public void _setCompoundColor (final TextView _view, final String _color) {
		int[][] states = new int[][] {
			    new int[] { android.R.attr.state_enabled}
		};
		int[] colors = new int[] {
			    Color.parseColor(_color)
		};
		android.content.res.ColorStateList list = new android.content.res.ColorStateList(states,colors);
		_view.setCompoundDrawableTintList(list);
	}
	
	
	public void _shapeRadius (final View _v, final String _color, final double _radius) {
		android.graphics.drawable.GradientDrawable shape = new android.graphics.drawable.GradientDrawable();
		  shape.setShape(android.graphics.drawable.GradientDrawable.RECTANGLE);
		
		shape.setCornerRadius((int)_radius);
		
		shape.setColor(Color.parseColor(_color));
		_v.setBackgroundDrawable(shape);
	}
	
	
	public void _shadow () {
		_shapeRadius(linear10, "#E0F1FD", SketchwareUtil.getDip(getApplicationContext(), (int)(15)));
		_shapeRadius(linear12, "#E0F1FD", SketchwareUtil.getDip(getApplicationContext(), (int)(15)));
		_shapeRadius(linear14, "#E0F1FD", SketchwareUtil.getDip(getApplicationContext(), (int)(15)));
		_shapeRadius(linear16, "#E0F1FD", SketchwareUtil.getDip(getApplicationContext(), (int)(15)));
		_shapeRadius(linear18, "#E0F1FD", SketchwareUtil.getDip(getApplicationContext(), (int)(15)));
		_shapeRadius(linear20, "#E0F1FD", SketchwareUtil.getDip(getApplicationContext(), (int)(15)));
	}
	
	
	public void _SetCompoundDrawable (final TextView _view, final double _location, final String _drawable_resId, final double _padding) {
		int resID = getResources().getIdentifier(_drawable_resId, "drawable", getPackageName());
		android.graphics.drawable.Drawable newDrawable = getResources().getDrawable(resID);
		android.graphics.drawable.Drawable drawableLeft = _view.getCompoundDrawables()[0];
		android.graphics.drawable.Drawable drawableTop = _view.getCompoundDrawables()[1];
		android.graphics.drawable.Drawable drawableRight = _view.getCompoundDrawables()[2];
		android.graphics.drawable.Drawable drawableBottom = _view.getCompoundDrawables()[3];
		switch((int)_location){
			case 0: //left
			_view.setCompoundDrawablesWithIntrinsicBounds(newDrawable,drawableTop,drawableRight,drawableBottom);
			break;
			case 1: //top
			_view.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,newDrawable,drawableRight,drawableBottom);
			break;
			case 2: //right
			_view.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,drawableTop,newDrawable,drawableBottom);
			break;
			case 3: //bottom
			_view.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,drawableTop,drawableRight,newDrawable);
			break;
		}
		_view.setCompoundDrawablePadding((int)_padding);
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