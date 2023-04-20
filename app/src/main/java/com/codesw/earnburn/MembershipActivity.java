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
import android.widget.ScrollView;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ChildEventListener;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;


public class MembershipActivity extends  AppCompatActivity  { 
	
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private String packaged = "";
	private String daily = "";
	private String lastChecked = "";
	private String validity = "";
	private double temp = 0;
	private String lastUpdate = "";
	private double n = 0;
	private String points = "";
	private String fontName = "";
	private String typeace = "";
	private boolean hasPackage = false;
	private HashMap<String, Object> map = new HashMap<>();
	
	private ArrayList<HashMap<String, Object>> list = new ArrayList<>();
	private ArrayList<String> uid = new ArrayList<>();
	
	private LinearLayout linear12;
	private LinearLayout linear14;
	private ImageView imageview1;
	private TextView textview3;
	private ImageView imageview2;
	private ScrollView vscroll1;
	private LinearLayout linear15;
	private LinearLayout plan1;
	private LinearLayout plan2;
	private LinearLayout plan3;
	private ImageView imageview3;
	private TextView textview4;
	private TextView textview25;
	private LinearLayout linear58;
	private LinearLayout linear16;
	private LinearLayout linear18;
	private LinearLayout linear19;
	private LinearLayout linear21;
	private LinearLayout linear22;
	private LinearLayout linear26;
	private LinearLayout linear24;
	private TextView textview27;
	private LinearLayout dot1;
	private TextView textview10;
	private LinearLayout dot2;
	private TextView textview11;
	private LinearLayout dot3;
	private TextView textview12;
	private LinearLayout dot4;
	private TextView textview13;
	private ImageView imageview5;
	private TextView textview8;
	private TextView textview24;
	private LinearLayout linear55;
	private LinearLayout linear27;
	private LinearLayout linear29;
	private LinearLayout linear30;
	private LinearLayout linear32;
	private LinearLayout linear33;
	private LinearLayout linear35;
	private LinearLayout linear36;
	private LinearLayout linear39;
	private LinearLayout linear40;
	private TextView textview1;
	private LinearLayout dot11;
	private TextView textview14;
	private LinearLayout dot22;
	private TextView textview15;
	private LinearLayout dot33;
	private TextView textview16;
	private LinearLayout dot44;
	private TextView textview17;
	private LinearLayout dot55;
	private TextView textview18;
	private ImageView imageview4;
	private TextView textview6;
	private TextView textview26;
	private LinearLayout linear56;
	private LinearLayout linear41;
	private LinearLayout linear43;
	private LinearLayout linear44;
	private LinearLayout linear47;
	private LinearLayout linear48;
	private LinearLayout linear50;
	private LinearLayout linear51;
	private LinearLayout linear53;
	private LinearLayout linear54;
	private TextView textview28;
	private LinearLayout dot111;
	private TextView textview19;
	private LinearLayout dot222;
	private TextView textview20;
	private LinearLayout dot444;
	private TextView textview21;
	private LinearLayout dot555;
	private TextView textview22;
	private LinearLayout dot666;
	private TextView textview23;
	
	private DatabaseReference Membership = _firebase.getReference("membership");
	private ChildEventListener _Membership_child_listener;
	private Calendar c = Calendar.getInstance();
	private Calendar d = Calendar.getInstance();
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
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.membership);
		initialize(_savedInstanceState);
		com.google.firebase.FirebaseApp.initializeApp(this);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		
		linear12 = (LinearLayout) findViewById(R.id.linear12);
		linear14 = (LinearLayout) findViewById(R.id.linear14);
		imageview1 = (ImageView) findViewById(R.id.imageview1);
		textview3 = (TextView) findViewById(R.id.textview3);
		imageview2 = (ImageView) findViewById(R.id.imageview2);
		vscroll1 = (ScrollView) findViewById(R.id.vscroll1);
		linear15 = (LinearLayout) findViewById(R.id.linear15);
		plan1 = (LinearLayout) findViewById(R.id.plan1);
		plan2 = (LinearLayout) findViewById(R.id.plan2);
		plan3 = (LinearLayout) findViewById(R.id.plan3);
		imageview3 = (ImageView) findViewById(R.id.imageview3);
		textview4 = (TextView) findViewById(R.id.textview4);
		textview25 = (TextView) findViewById(R.id.textview25);
		linear58 = (LinearLayout) findViewById(R.id.linear58);
		linear16 = (LinearLayout) findViewById(R.id.linear16);
		linear18 = (LinearLayout) findViewById(R.id.linear18);
		linear19 = (LinearLayout) findViewById(R.id.linear19);
		linear21 = (LinearLayout) findViewById(R.id.linear21);
		linear22 = (LinearLayout) findViewById(R.id.linear22);
		linear26 = (LinearLayout) findViewById(R.id.linear26);
		linear24 = (LinearLayout) findViewById(R.id.linear24);
		textview27 = (TextView) findViewById(R.id.textview27);
		dot1 = (LinearLayout) findViewById(R.id.dot1);
		textview10 = (TextView) findViewById(R.id.textview10);
		dot2 = (LinearLayout) findViewById(R.id.dot2);
		textview11 = (TextView) findViewById(R.id.textview11);
		dot3 = (LinearLayout) findViewById(R.id.dot3);
		textview12 = (TextView) findViewById(R.id.textview12);
		dot4 = (LinearLayout) findViewById(R.id.dot4);
		textview13 = (TextView) findViewById(R.id.textview13);
		imageview5 = (ImageView) findViewById(R.id.imageview5);
		textview8 = (TextView) findViewById(R.id.textview8);
		textview24 = (TextView) findViewById(R.id.textview24);
		linear55 = (LinearLayout) findViewById(R.id.linear55);
		linear27 = (LinearLayout) findViewById(R.id.linear27);
		linear29 = (LinearLayout) findViewById(R.id.linear29);
		linear30 = (LinearLayout) findViewById(R.id.linear30);
		linear32 = (LinearLayout) findViewById(R.id.linear32);
		linear33 = (LinearLayout) findViewById(R.id.linear33);
		linear35 = (LinearLayout) findViewById(R.id.linear35);
		linear36 = (LinearLayout) findViewById(R.id.linear36);
		linear39 = (LinearLayout) findViewById(R.id.linear39);
		linear40 = (LinearLayout) findViewById(R.id.linear40);
		textview1 = (TextView) findViewById(R.id.textview1);
		dot11 = (LinearLayout) findViewById(R.id.dot11);
		textview14 = (TextView) findViewById(R.id.textview14);
		dot22 = (LinearLayout) findViewById(R.id.dot22);
		textview15 = (TextView) findViewById(R.id.textview15);
		dot33 = (LinearLayout) findViewById(R.id.dot33);
		textview16 = (TextView) findViewById(R.id.textview16);
		dot44 = (LinearLayout) findViewById(R.id.dot44);
		textview17 = (TextView) findViewById(R.id.textview17);
		dot55 = (LinearLayout) findViewById(R.id.dot55);
		textview18 = (TextView) findViewById(R.id.textview18);
		imageview4 = (ImageView) findViewById(R.id.imageview4);
		textview6 = (TextView) findViewById(R.id.textview6);
		textview26 = (TextView) findViewById(R.id.textview26);
		linear56 = (LinearLayout) findViewById(R.id.linear56);
		linear41 = (LinearLayout) findViewById(R.id.linear41);
		linear43 = (LinearLayout) findViewById(R.id.linear43);
		linear44 = (LinearLayout) findViewById(R.id.linear44);
		linear47 = (LinearLayout) findViewById(R.id.linear47);
		linear48 = (LinearLayout) findViewById(R.id.linear48);
		linear50 = (LinearLayout) findViewById(R.id.linear50);
		linear51 = (LinearLayout) findViewById(R.id.linear51);
		linear53 = (LinearLayout) findViewById(R.id.linear53);
		linear54 = (LinearLayout) findViewById(R.id.linear54);
		textview28 = (TextView) findViewById(R.id.textview28);
		dot111 = (LinearLayout) findViewById(R.id.dot111);
		textview19 = (TextView) findViewById(R.id.textview19);
		dot222 = (LinearLayout) findViewById(R.id.dot222);
		textview20 = (TextView) findViewById(R.id.textview20);
		dot444 = (LinearLayout) findViewById(R.id.dot444);
		textview21 = (TextView) findViewById(R.id.textview21);
		dot555 = (LinearLayout) findViewById(R.id.dot555);
		textview22 = (TextView) findViewById(R.id.textview22);
		dot666 = (LinearLayout) findViewById(R.id.dot666);
		textview23 = (TextView) findViewById(R.id.textview23);
		fAuth = FirebaseAuth.getInstance();
		
		imageview2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setClass(getApplicationContext(), TransectionActivity.class);
				startActivity(i);
			}
		});
		
		plan1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_rippleRoundStroke(plan1, "#FFFFFF", "#EEEEEE", 20, 3, "#CA7425");
				_rippleRoundStroke(plan2, "#FFFFFF", "#EEEEEE", 20, 2, "#EEEEEE");
				_rippleRoundStroke(plan3, "#FFFFFF", "#EEEEEE", 20, 2, "#EEEEEE");
				_autoTransitionScroll(vscroll1);
				textview27.setVisibility(View.VISIBLE);
				textview28.setVisibility(View.GONE);
				textview1.setVisibility(View.GONE);
			}
		});
		
		plan2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_rippleRoundStroke(plan1, "#FFFFFF", "#EEEEEE", 20, 2, "#EEEEEE");
				_rippleRoundStroke(plan2, "#FFFFFF", "#EEEEEE", 20, 3, "#829FBA");
				_rippleRoundStroke(plan3, "#FFFFFF", "#EEEEEE", 20, 2, "#EEEEEE");
				_autoTransitionScroll(vscroll1);
				textview27.setVisibility(View.GONE);
				textview28.setVisibility(View.GONE);
				textview1.setVisibility(View.VISIBLE);
			}
		});
		
		plan3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_rippleRoundStroke(plan1, "#FFFFFF", "#EEEEEE", 20, 2, "#EEEEEE");
				_rippleRoundStroke(plan2, "#FFFFFF", "#EEEEEE", 20, 2, "#EEEEEE");
				_rippleRoundStroke(plan3, "#FFFFFF", "#EEEEEE", 20, 3, "#FD960C");
				_autoTransitionScroll(vscroll1);
				textview27.setVisibility(View.GONE);
				textview28.setVisibility(View.VISIBLE);
				textview1.setVisibility(View.GONE);
			}
		});
		
		textview27.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (isConnected()) {
					if (hasPackage) {
						SketchwareUtil.showMessage(getApplicationContext(), "You've already have a package");
					}
					else {
						c = Calendar.getInstance();
						c.add(Calendar.DAY_OF_MONTH, (int)(20));
						i.setClass(getApplicationContext(), PaymentActivity.class);
						i.putExtra("plan", "Bronze");
						i.putExtra("validity", new SimpleDateFormat("dd/MM/yy hh:mm:ss").format(c.getTime()));
						i.putExtra("percent", "10");
						i.putExtra("minimum", "20");
						i.putExtra("days", "20");
						startActivity(i);
					}
				}
				else {
					SketchwareUtil.showMessage(getApplicationContext(), "No internet");
				}
			}
		});
		
		textview1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (isConnected()) {
					if (hasPackage) {
						SketchwareUtil.showMessage(getApplicationContext(), "You've already have a package");
					}
					else {
						c = Calendar.getInstance();
						c.add(Calendar.DAY_OF_MONTH, (int)(30));
						i.setClass(getApplicationContext(), PaymentActivity.class);
						i.putExtra("plan", "Diamond");
						i.putExtra("validity", new SimpleDateFormat("dd/MM/yy hh:mm:ss").format(c.getTime()));
						i.putExtra("percent", "20");
						i.putExtra("minimum", "40");
						i.putExtra("days", "30");
						startActivity(i);
					}
				}
				else {
					SketchwareUtil.showMessage(getApplicationContext(), "No internet");
				}
			}
		});
		
		textview28.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (isConnected()) {
					if (hasPackage) {
						SketchwareUtil.showMessage(getApplicationContext(), "You've already have a package");
					}
					else {
						c = Calendar.getInstance();
						c.add(Calendar.DAY_OF_MONTH, (int)(50));
						i.setClass(getApplicationContext(), PaymentActivity.class);
						i.putExtra("plan", "Golden");
						i.putExtra("validity", new SimpleDateFormat("dd/MM/yy hh:mm:ss").format(c.getTime()));
						i.putExtra("percent", "30");
						i.putExtra("minimum", "60");
						i.putExtra("days", "50");
						startActivity(i);
					}
				}
				else {
					SketchwareUtil.showMessage(getApplicationContext(), "No internet");
				}
			}
		});
		
		_Membership_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if (_childKey.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
					if (_childValue.get("plan").toString().equals("false")) {
						if (_childValue.containsKey("status")) {
							if (_childValue.get("status").toString().equals("pending") || _childValue.get("status").toString().equals("verified")) {
								hasPackage = true;
							}
							else {
								hasPackage = false;
							}
						}
						else {
							hasPackage = false;
						}
					}
					else {
						hasPackage = true;
					}
				}
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if (_childKey.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
					if (_childValue.get("plan").toString().equals("false")) {
						if (_childValue.containsKey("status")) {
							if (_childValue.get("status").toString().equals("pending") || _childValue.get("status").toString().equals("verified")) {
								hasPackage = true;
							}
							else {
								hasPackage = false;
							}
						}
						else {
							hasPackage = false;
						}
					}
					else {
						hasPackage = true;
					}
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
		_ICC(imageview1, "#616161", "#232323");
		_RippleEffects("#EEEEEE", imageview1);
		_ICC(imageview2, "#616161", "#232323");
		_RippleEffects("#EEEEEE", imageview2);
		_changeActivityFont("en_light");
		_NavStatusBarColor("#FFFFFF", "#FFFFFF");
		_DARK_ICONS();
		_removeScollBar(vscroll1);
		_rippleRoundStroke(plan1, "#FFFFFF", "#EEEEEE", 20, 2, "#EEEEEE");
		_rippleRoundStroke(plan2, "#FFFFFF", "#EEEEEE", 20, 2, "#EEEEEE");
		_rippleRoundStroke(plan3, "#FFFFFF", "#EEEEEE", 20, 2, "#EEEEEE");
		_RoundAndBorder(dot1, "#EEEEEE", 0, "#FF5252", 100);
		_RoundAndBorder(dot4, "#EEEEEE", 0, "#FF5252", 100);
		_RoundAndBorder(dot3, "#00C853", 0, "#FF5252", 100);
		_RoundAndBorder(dot2, "#00C853", 0, "#FF5252", 100);
		_RoundAndBorder(dot11, "#00C853", 0, "#FF5252", 100);
		_RoundAndBorder(dot22, "#00C853", 0, "#FF5252", 100);
		_RoundAndBorder(dot33, "#00C853", 0, "#FF5252", 100);
		_RoundAndBorder(dot44, "#00C853", 0, "#FF5252", 100);
		_RoundAndBorder(dot55, "#00C853", 0, "#FF5252", 100);
		_RoundAndBorder(dot111, "#00C853", 0, "#FF5252", 100);
		_RoundAndBorder(dot222, "#00C853", 0, "#FF5252", 100);
		_RoundAndBorder(dot444, "#00C853", 0, "#FF5252", 100);
		_RoundAndBorder(dot555, "#00C853", 0, "#FF5252", 100);
		_RoundAndBorder(dot666, "#00C853", 0, "#FF5252", 100);
		_rippleRoundStroke(textview1, "#FF829FBA", "#40FFFFFF", 20, 0, "#000000");
		_rippleRoundStroke(textview27, "#FFCA7324", "#40FFFFFF", 20, 0, "#000000");
		_rippleRoundStroke(textview28, "#FFFD960C", "#40FFFFFF", 20, 0, "#000000");
		textview27.setVisibility(View.GONE);
		textview28.setVisibility(View.GONE);
		textview1.setVisibility(View.GONE);
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			
			default:
			break;
		}
	}
	
	public void _rippleRoundStroke (final View _view, final String _focus, final String _pressed, final double _round, final double _stroke, final String _strokeclr) {
		android.graphics.drawable.GradientDrawable GG = new android.graphics.drawable.GradientDrawable();
		GG.setColor(Color.parseColor(_focus));
		GG.setCornerRadius((float)_round);
		GG.setStroke((int) _stroke,
		Color.parseColor("#" + _strokeclr.replace("#", "")));
		android.graphics.drawable.RippleDrawable RE = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ Color.parseColor(_pressed)}), GG, null);
		_view.setBackground(RE);
	}
	
	
	public void _RoundAndBorder (final View _view, final String _color1, final double _border, final String _color2, final double _round) {
		android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
		gd.setColor(Color.parseColor(_color1));
		gd.setCornerRadius((int) _round);
		gd.setStroke((int) _border, Color.parseColor(_color2));
		_view.setBackground(gd);
	}
	
	
	public void _DARK_ICONS () {
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
	}
	
	
	public void _NavStatusBarColor (final String _color1, final String _color2) {
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
			Window w = this.getWindow();	w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);	w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			w.setStatusBarColor(Color.parseColor("#" + _color1.replace("#", "")));	w.setNavigationBarColor(Color.parseColor("#" + _color2.replace("#", "")));
		}
	}
	
	
	public void _RippleEffects (final String _color, final View _view) {
		android.content.res.ColorStateList clr = new android.content.res.ColorStateList(new int[][]{new int[]{}},new int[]{Color.parseColor(_color)});
		android.graphics.drawable.RippleDrawable ripdr = new android.graphics.drawable.RippleDrawable(clr, null, null);
		_view.setBackground(ripdr);
	}
	
	
	public void _ICC (final ImageView _img, final String _c1, final String _c2) {
		_img.setImageTintList(new android.content.res.ColorStateList(new int[][] {{-android.R.attr.state_pressed},{android.R.attr.state_pressed}},new int[]{Color.parseColor(_c1), Color.parseColor(_c2)}));
	}
	
	
	public void _changeActivityFont (final String _fontname) {
		fontName = "fonts/".concat(_fontname.concat(".ttf"));
		overrideFonts(this,getWindow().getDecorView()); 
	} 
	private void overrideFonts(final android.content.Context context, final View v) {
		
		try {
			Typeface 
			typeace = Typeface.createFromAsset(getAssets(), fontName);;
			if ((v instanceof ViewGroup)) {
				ViewGroup vg = (ViewGroup) v;
				for (int i = 0;
				i < vg.getChildCount();
				i++) {
					View child = vg.getChildAt(i);
					overrideFonts(context, child);
				}
			}
			else {
				if ((v instanceof TextView)) {
					((TextView) v).setTypeface(typeace);
				}
				else {
					if ((v instanceof EditText )) {
						((EditText) v).setTypeface(typeace);
					}
					else {
						if ((v instanceof Button)) {
							((Button) v).setTypeface(typeace);
						}
					}
				}
			}
		}
		catch(Exception e)
		
		{
			SketchwareUtil.showMessage(getApplicationContext(), "Error Loading Font");
		};
	}
	
	
	public void _removeScollBar (final View _view) {
		_view.setVerticalScrollBarEnabled(false); _view.setHorizontalScrollBarEnabled(false);
	}
	
	
	public void _shadAnim (final View _view, final String _propertyName, final double _value, final double _duration) {
		ObjectAnimator anim = new ObjectAnimator();
		anim.setTarget(_view);
		anim.setPropertyName(_propertyName);
		anim.setFloatValues((float)_value);
		anim.setDuration((long)_duration);
		anim.start();
	}
	
	
	public void _autoTransitionScroll (final View _scroll) {
		android.transition.TransitionManager.beginDelayedTransition((ScrollView)_scroll, new android.transition.AutoTransition());
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