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
import android.widget.Button;
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
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.app.Activity;
import android.content.SharedPreferences;
import android.view.View;
import android.graphics.Typeface;
import java.text.DecimalFormat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;


public class PeerToPeerActivity extends  AppCompatActivity  { 
	
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private Toolbar _toolbar;
	private AppBarLayout _app_bar;
	private CoordinatorLayout _coordinator;
	private HashMap<String, Object> map = new HashMap<>();
	private String points = "";
	private HashMap<String, Object> mapA = new HashMap<>();
	private HashMap<String, Object> mapvar = new HashMap<>();
	private String sender = "";
	private String receiver = "";
	private boolean trueUID = false;
	private String password = "";
	
	private ArrayList<HashMap<String, Object>> list = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> user = new ArrayList<>();
	
	private LinearLayout linear1;
	private LinearLayout linear6;
	private TextView textview3;
	private ListView listview1;
	private LinearLayout linear2;
	private LinearLayout linear8;
	private LinearLayout textinputlayout1;
	private LinearLayout textinputlayout2;
	private Button button1;
	private TextView textview1;
	private EditText edittext1;
	private EditText edittext2;
	
	private DatabaseReference Transfer = _firebase.getReference("transfer");
	private ChildEventListener _Transfer_child_listener;
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
	private AlertDialog.Builder dialog_add;
	private SharedPreferences sp;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.peer_to_peer);
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
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		linear6 = (LinearLayout) findViewById(R.id.linear6);
		textview3 = (TextView) findViewById(R.id.textview3);
		listview1 = (ListView) findViewById(R.id.listview1);
		linear2 = (LinearLayout) findViewById(R.id.linear2);
		linear8 = (LinearLayout) findViewById(R.id.linear8);
		textinputlayout1 = (LinearLayout) findViewById(R.id.textinputlayout1);
		textinputlayout2 = (LinearLayout) findViewById(R.id.textinputlayout2);
		button1 = (Button) findViewById(R.id.button1);
		textview1 = (TextView) findViewById(R.id.textview1);
		edittext1 = (EditText) findViewById(R.id.edittext1);
		edittext2 = (EditText) findViewById(R.id.edittext2);
		fAuth = FirebaseAuth.getInstance();
		dialog_add = new AlertDialog.Builder(this);
		sp = getSharedPreferences("sp", Activity.MODE_PRIVATE);
		
		button1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (edittext1.getText().toString().equals("") || edittext2.getText().toString().equals("")) {
					SketchwareUtil.showMessage(getApplicationContext(), "Please check input");
				}
				else {
					if (isConnected()) {
						if (Double.parseDouble(edittext1.getText().toString()) < Double.parseDouble(points)) {
							trueUID = false;
							userdb.addListenerForSingleValueEvent(new ValueEventListener() {
								@Override
								public void onDataChange(DataSnapshot _dataSnapshot) {
									user = new ArrayList<>();
									try {
										GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
										for (DataSnapshot _data : _dataSnapshot.getChildren()) {
											HashMap<String, Object> _map = _data.getValue(_ind);
											user.add(_map);
										}
									}
									catch (Exception _e) {
										_e.printStackTrace();
									}
									for(HashMap<String,Object> hmap: user){
													for(Object str:hmap.keySet()){
															
											if (hmap.get("uid").equals(edittext2.getText().toString())) {
												trueUID = true;
												receiver = hmap.get("points").toString();;
											}
										}}
									if (trueUID) {
										dialog_add.setTitle("Enter TRX password");
										final EditText dialog_edittext1 = new EditText(PeerToPeerActivity.this);
										dialog_edittext1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
										dialog_edittext1.setHint("Enter Password");
										
										
										View inflated_view = getLayoutInflater().inflate(R.layout.edittext, null);
										
										LinearLayout dialog_linear1 = inflated_view.findViewById(R.id.linear2);
										
										dialog_linear1.addView(dialog_edittext1);
										dialog_add.setView(inflated_view);
										dialog_add.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
											@Override
											public void onClick(DialogInterface _dialog, int _which) {
												password = dialog_edittext1.getText().toString();
												if (password.equals(sp.getString("trxPw", ""))) {
													mapA = new HashMap<>();
													mapA.put("points", String.valueOf(Double.parseDouble(receiver) + Double.parseDouble(edittext1.getText().toString())));
													userdb.child(edittext2.getText().toString()).updateChildren(mapA);
													mapvar = new HashMap<>();
													mapvar.put("points", String.valueOf(Double.parseDouble(points) - Double.parseDouble(edittext1.getText().toString())));
													userdb.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(mapvar);
													map = new HashMap<>();
													map.put("sender", FirebaseAuth.getInstance().getCurrentUser().getUid());
													map.put("receiver", edittext2.getText().toString());
													map.put("amount", edittext1.getText().toString());
													Transfer.child(Transfer.push().getKey()).updateChildren(map);
													SketchwareUtil.showMessage(getApplicationContext(), "Transfer success");
												}
												else {
													SketchwareUtil.showMessage(getApplicationContext(), "TRX password didn't match");
												}
											}
										});
										dialog_add.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
											@Override
											public void onClick(DialogInterface _dialog, int _which) {
												
											}
										});
										dialog_add.create().show();
									}
									else {
										SketchwareUtil.showMessage(getApplicationContext(), "User doesn't exist on the server");
									}
								}
								@Override
								public void onCancelled(DatabaseError _databaseError) {
								}
							});
						}
						else {
							SketchwareUtil.showMessage(getApplicationContext(), "No enough money");
						}
					}
					else {
						SketchwareUtil.showMessage(getApplicationContext(), "No Internet");
					}
				}
			}
		});
		
		_Transfer_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if (_childValue.containsValue(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
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
		Transfer.addChildEventListener(_Transfer_child_listener);
		
		_userdb_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if (_childKey.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
					points = _childValue.get("points").toString();
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
		_shapeRadius(button1, "#2196F3", 30);
		_shapeRadius(linear8, "#1060C7", SketchwareUtil.getDip(getApplicationContext(), (int)(10)));
		_shapeRadius(linear2, "#FAFAFA", 15);
		_shapeRadius(linear6, "#E0F1FD", SketchwareUtil.getDip(getApplicationContext(), (int)(15)));
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
		textinput2.setHint("Enter Receiver UID");
		textinput2.setHintAnimationEnabled(true);
		textinput2.setBoxBackgroundMode(textinput.BOX_BACKGROUND_OUTLINE);
		textinput2.addView(edittext2);
		textinputlayout2.addView(textinput2);
		textview1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/montserrat_medium.ttf"), 0);
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
				_view = _inflater.inflate(R.layout.transfer, null);
			}
			
			final LinearLayout linear2 = (LinearLayout) _view.findViewById(R.id.linear2);
			final LinearLayout linear1 = (LinearLayout) _view.findViewById(R.id.linear1);
			final ImageView imageview1 = (ImageView) _view.findViewById(R.id.imageview1);
			final LinearLayout linear3 = (LinearLayout) _view.findViewById(R.id.linear3);
			final TextView textview1 = (TextView) _view.findViewById(R.id.textview1);
			final TextView textview2 = (TextView) _view.findViewById(R.id.textview2);
			
			if (_data.get((int)_position).get("sender").toString().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
				imageview1.setRotation((float)(180));
				textview1.setText("To User : ".concat(_data.get((int)_position).get("receiver").toString()));
			}
			else {
				imageview1.setRotation((float)(0));
				textview1.setText("From User : ".concat(_data.get((int)_position).get("sender").toString()));
			}
			textview2.setText(_data.get((int)_position).get("amount").toString().concat(" D-50C"));
			textview1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/montserrat_medium.ttf"), 0);
			textview2.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/montserrat_medium.ttf"), 0);
			_shapeRadius(linear1, "#FAFAFA", 15);
			_shapeRadius(linear2, "#E0F1FD", SketchwareUtil.getDip(getApplicationContext(), (int)(15)));
			
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