package com.codesw.earnburn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.*;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.appbar.AppBarLayout;
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
import android.widget.TextView;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
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
import java.util.Calendar;
import java.text.SimpleDateFormat;
import android.view.View;
import java.text.DecimalFormat;
import android.graphics.Typeface;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;


public class WithdrawActivity extends  AppCompatActivity  { 
	
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private Toolbar _toolbar;
	private AppBarLayout _app_bar;
	private CoordinatorLayout _coordinator;
	private HashMap<String, Object> map = new HashMap<>();
	private double points = 0;
	private HashMap<String, Object> mapvar = new HashMap<>();
	
	private ArrayList<HashMap<String, Object>> list = new ArrayList<>();
	
	private LinearLayout linear7;
	private LinearLayout linear6;
	private TextView textview3;
	private ListView listview1;
	private LinearLayout linear1;
	private LinearLayout linear5;
	private LinearLayout textinputlayout1;
	private LinearLayout textinputlayout2;
	private LinearLayout linear4;
	private LinearLayout linear3;
	private TextView textview1;
	private EditText edittext1;
	private EditText edittext2;
	private TextView textview2;
	
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
	private Calendar c = Calendar.getInstance();
	private DatabaseReference Withdraw = _firebase.getReference("withdraw");
	private ChildEventListener _Withdraw_child_listener;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.withdraw);
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
		linear7 = (LinearLayout) findViewById(R.id.linear7);
		linear6 = (LinearLayout) findViewById(R.id.linear6);
		textview3 = (TextView) findViewById(R.id.textview3);
		listview1 = (ListView) findViewById(R.id.listview1);
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		linear5 = (LinearLayout) findViewById(R.id.linear5);
		textinputlayout1 = (LinearLayout) findViewById(R.id.textinputlayout1);
		textinputlayout2 = (LinearLayout) findViewById(R.id.textinputlayout2);
		linear4 = (LinearLayout) findViewById(R.id.linear4);
		linear3 = (LinearLayout) findViewById(R.id.linear3);
		textview1 = (TextView) findViewById(R.id.textview1);
		edittext1 = (EditText) findViewById(R.id.edittext1);
		edittext2 = (EditText) findViewById(R.id.edittext2);
		textview2 = (TextView) findViewById(R.id.textview2);
		fAuth = FirebaseAuth.getInstance();
		
		linear4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (!edittext1.getText().toString().equals("") || !edittext2.getText().toString().equals("")) {
					if (isConnected()) {
						if (Double.parseDouble(edittext1.getText().toString()) < points) {
							if (points < 0.6d) {
								SketchwareUtil.showMessage(getApplicationContext(), "Minimum amount is 0.6 D-50C");
							}
							else {
								c = Calendar.getInstance();
								map = new HashMap<>();
								map.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
								map.put("amount", edittext1.getText().toString());
								map.put("status", "pending");
								map.put("address", edittext2.getText().toString());
								map.put("date", new SimpleDateFormat("dd-MM-yy").format(c.getTime()));
								Withdraw.child(Withdraw.push().getKey()).updateChildren(map);
								mapvar = new HashMap<>();
								mapvar.put("points", String.valueOf(points - Double.parseDouble(edittext1.getText().toString())));
								userdb.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(mapvar);
								SketchwareUtil.showMessage(getApplicationContext(), "Withdraw Added");
							}
						}
						else {
							SketchwareUtil.showMessage(getApplicationContext(), "You don't have sufficient balance");
						}
					}
					else {
						SketchwareUtil.showMessage(getApplicationContext(), "No internet");
					}
				}
				else {
					SketchwareUtil.showMessage(getApplicationContext(), "Please check input");
				}
			}
		});
		
		_userdb_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if (_childKey.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
					points = Double.parseDouble(_childValue.get("points").toString());
				}
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if (_childKey.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
					points = Double.parseDouble(_childValue.get("points").toString());
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
		
		_Withdraw_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if (_childValue.get("uid").toString().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
					list.add(_childValue);
				}
				listview1.setAdapter(new Listview1Adapter(list));
				((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
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
		Withdraw.addChildEventListener(_Withdraw_child_listener);
		
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
		setTitle("Withdraw");
		_radius_to(linear4, 30, 5, "#1060C7");
		_shapeRadius(linear3, "#1060C7", SketchwareUtil.getDip(getApplicationContext(), (int)(10)));
		_shapeRadius(linear1, "#FAFAFA", 15);
		_shapeRadius(linear6, "#E0F1FD", SketchwareUtil.getDip(getApplicationContext(), (int)(15)));
		textview2.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/montserrat_medium.ttf"), 0);
		textview1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/montserrat_medium.ttf"), 0);
		textview3.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/montserrat_medium.ttf"), 0);
		Toolbar.LayoutParams params = new Toolbar.LayoutParams(Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.MATCH_PARENT);
		params.gravity = Gravity.CENTER;
		
		params.topMargin = 20;
		                    ((TextView)_toolbar.getChildAt(0)).setLayoutParams(params);
		
		((TextView)_toolbar.getChildAt(0)).setTextSize(18);
		
		((TextView)_toolbar.getChildAt(0)).setGravity(Gravity.CENTER);
		
		((TextView)_toolbar.getChildAt(0)).setTextColor(Color.parseColor("#000000"));
		
		((TextView)_toolbar.getChildAt(0)).setTypeface(Typeface.createFromAsset(getAssets(),"fonts/manrope_bold.ttf"), 0);
		
		((ImageView)_toolbar.getChildAt(1)).setImageResource(R.drawable.ic_arrow_back_black);
			com.google.android.material.appbar.AppBarLayout appBarLayout = (com.google.android.material.appbar.AppBarLayout) _toolbar.getParent();
				appBarLayout.setStateListAnimator(null);
		textinputlayout1.removeView(edittext1);
		final com.google.android.material.textfield.TextInputLayout textinput = new com.google.android.material.textfield.TextInputLayout (this);
		 textinput.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)); 
		//textinput.setBoxBackgroundColor(0xFFFFFFFF);
		textinput.setBoxStrokeColor(0xFF0D47A1);
		textinput.setBoxCornerRadii(5, 5, 5, 5);
		textinput.setPadding((int)8, (int)0 , (int)0 , (int)0 );
		textinput.setHintEnabled(true);
		textinput.setHint("Enter Amount(D-50C)");
		textinput.setHintAnimationEnabled(true);
		textinput.setBoxBackgroundMode(textinput.BOX_BACKGROUND_OUTLINE);
		textinput.addView(edittext1);
		textinputlayout1.addView(textinput);
		textinputlayout2.removeView(edittext2);
		final com.google.android.material.textfield.TextInputLayout textinput2 = new com.google.android.material.textfield.TextInputLayout (this);
		 textinput2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)); 
		//textinput.setBoxBackgroundColor(0xFFFFFFFF);
		textinput2.setBoxStrokeColor(0xFF0D47A1);
		textinput2.setBoxCornerRadii(5, 5, 5, 5);
		textinput2.setPadding((int)8, (int)0 , (int)0 , (int)0 );
		textinput2.setHintEnabled(true);
		textinput2.setHint("Enter Etherium Wallet Address");
		textinput2.setHintAnimationEnabled(true);
		textinput2.setBoxBackgroundMode(textinput.BOX_BACKGROUND_OUTLINE);
		textinput2.addView(edittext2);
		textinputlayout2.addView(textinput2);
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			
			default:
			break;
		}
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
				_view = _inflater.inflate(R.layout.cust, null);
			}
			
			final LinearLayout linear2 = (LinearLayout) _view.findViewById(R.id.linear2);
			final LinearLayout linear1 = (LinearLayout) _view.findViewById(R.id.linear1);
			final ImageView imageview1 = (ImageView) _view.findViewById(R.id.imageview1);
			final LinearLayout linear3 = (LinearLayout) _view.findViewById(R.id.linear3);
			final TextView textview1 = (TextView) _view.findViewById(R.id.textview1);
			final TextView textview2 = (TextView) _view.findViewById(R.id.textview2);
			
			_shapeRadius(linear1, "#FAFAFA", 15);
			_shapeRadius(linear2, "#E0F1FD", SketchwareUtil.getDip(getApplicationContext(), (int)(15)));
			textview2.setText(list.get((int)_position).get("status").toString());
			textview1.setText(list.get((int)_position).get("amount").toString().concat(" D-50C"));
			if (list.get((int)_position).get("status").toString().equals("pending")) {
				textview2.setTextColor(0xFFFFEB3B);
			}
			else {
				if (list.get((int)_position).get("status").toString().equals("approved")) {
					textview2.setTextColor(0xFF0084FF);
				}
				else {
					if (list.get((int)_position).get("status").toString().equals("paid")) {
						textview2.setTextColor(0xFF00E676);
					}
					else {
						textview2.setTextColor(0xFF000000);
					}
				}
			}
			textview1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/montserrat_medium.ttf"), 0);
			textview2.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/montserrat_medium.ttf"), 0);
			
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