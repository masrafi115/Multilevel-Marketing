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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.widget.EditText;
import java.util.Calendar;
import java.text.SimpleDateFormat;
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
import android.view.View;
import java.text.DecimalFormat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;


public class PaymentActivity extends  AppCompatActivity  { 
	
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private HashMap<String, Object> map = new HashMap<>();
	
	private ArrayList<String> value = new ArrayList<>();
	private ArrayList<String> uplineValues = new ArrayList<>();
	
	private LinearLayout linear19;
	private LinearLayout linear1;
	private ImageView imageview7;
	private TextView textview1;
	private ImageView imageview8;
	private LinearLayout layout_litcoin;
	private LinearLayout linear4;
	private LinearLayout linear21;
	private LinearLayout linear5;
	private LinearLayout textinputlayout1;
	private LinearLayout textinputlayout2;
	private Button button2;
	private ImageView imageview1;
	private EditText edittext1;
	private EditText edittext2;
	
	private Calendar c = Calendar.getInstance();
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
	private DatabaseReference Membership = _firebase.getReference("membership");
	private ChildEventListener _Membership_child_listener;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.payment);
		initialize(_savedInstanceState);
		com.google.firebase.FirebaseApp.initializeApp(this);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		
		linear19 = (LinearLayout) findViewById(R.id.linear19);
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		imageview7 = (ImageView) findViewById(R.id.imageview7);
		textview1 = (TextView) findViewById(R.id.textview1);
		imageview8 = (ImageView) findViewById(R.id.imageview8);
		layout_litcoin = (LinearLayout) findViewById(R.id.layout_litcoin);
		linear4 = (LinearLayout) findViewById(R.id.linear4);
		linear21 = (LinearLayout) findViewById(R.id.linear21);
		linear5 = (LinearLayout) findViewById(R.id.linear5);
		textinputlayout1 = (LinearLayout) findViewById(R.id.textinputlayout1);
		textinputlayout2 = (LinearLayout) findViewById(R.id.textinputlayout2);
		button2 = (Button) findViewById(R.id.button2);
		imageview1 = (ImageView) findViewById(R.id.imageview1);
		edittext1 = (EditText) findViewById(R.id.edittext1);
		edittext2 = (EditText) findViewById(R.id.edittext2);
		fAuth = FirebaseAuth.getInstance();
		
		button2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (edittext1.getText().toString().equals("") || edittext2.getText().toString().equals("")) {
					SketchwareUtil.showMessage(getApplicationContext(), "Input can't Null");
				}
				else {
					if (Double.parseDouble(edittext1.getText().toString()) < Double.parseDouble(getIntent().getStringExtra("minimum"))) {
						SketchwareUtil.showMessage(getApplicationContext(), "Minimum ".concat(getIntent().getStringExtra("minimum").concat("$ for Investment")));
					}
					else {
						if (isConnected()) {
							map = new HashMap<>();
							map.put("plans", getIntent().getStringExtra("plan"));
							map.put("address", edittext2.getText().toString());
							map.put("amount", edittext1.getText().toString());
							map.put("daily", String.valueOf((((Double.parseDouble(getIntent().getStringExtra("percent")) * Double.parseDouble(edittext1.getText().toString())) / 100) / Double.parseDouble(getIntent().getStringExtra("days"))) * 0.117d));
							map.put("status", "pending");
							map.put("validity", getIntent().getStringExtra("validity"));
							map.put("plan", "false");
							map.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
							Membership.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(map);
							SketchwareUtil.showMessage(getApplicationContext(), "Payment added, please wait for verification");
							finish();
						}
						else {
							SketchwareUtil.showMessage(getApplicationContext(), "No internet");
						}
					}
				}
			}
		});
		
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
		imageview7.setColorFilter(0xFF722AE7, PorterDuff.Mode.MULTIPLY);
		imageview8.setColorFilter(0xFFFF4698, PorterDuff.Mode.MULTIPLY);
		_shapeRadius(button2, "#2196F3", 30);
		textinputlayout1.removeView(edittext1);
		final com.google.android.material.textfield.TextInputLayout textinput = new com.google.android.material.textfield.TextInputLayout (this);
		 textinput.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)); 
		//textinput.setBoxBackgroundColor(0xFFFFFFFF);
		textinput.setBoxStrokeColor(0xFF0D47A1);
		textinput.setBoxCornerRadii(5, 5, 5, 5);
		textinput.setPadding((int)8, (int)0 , (int)0 , (int)0 );
		textinput.setHintEnabled(true);
		textinput.setHint("Enter Investment Amount(USD)");
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
	
	
	public void _isConnected () {
	} private boolean isConnected() {
				ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		
				return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
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