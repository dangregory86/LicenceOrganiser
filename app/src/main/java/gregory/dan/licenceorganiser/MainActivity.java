package gregory.dan.licenceorganiser;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import gregory.dan.licenceorganiser.UI.UnitRecyclerViewAdapter;
import gregory.dan.licenceorganiser.Unit.Unit;
import gregory.dan.licenceorganiser.Unit.viewModels.MyViewModel;
import gregory.dan.licenceorganiser.firebase.FireBaseDatabaseUtilities;
import gregory.dan.qdlibrary.QDCalculator;

import static gregory.dan.licenceorganiser.AddUnitActivity.UNIT_NAME_EXTRA;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, UnitRecyclerViewAdapter.ListItemClickListener {

    @BindView(R.id.unit_list_recycler_view)
    RecyclerView mRecyclerView;
    TextView mUserTextView;
    private UnitRecyclerViewAdapter mUnitRecyclerViewAdapter;
    private MyViewModel myViewModel;
    private List<Unit> mUnits;

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference databaseReference;

    //TODO add edit buttons to edit all items
    //TODO implement notifications

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddUnitActivity.class);
                startActivity(intent);
            }
        });

        mAuth = FirebaseAuth.getInstance();

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myViewModel = ViewModelProviders.of(this).get(MyViewModel.class);
        myViewModel.deleteAllUnits();

        new FireBaseDatabaseUtilities(myViewModel).setupDatabase();
        databaseReference = mFirebaseDatabase.getReference();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        mUserTextView = headerView.findViewById(R.id.user_text_view);
        navigationView.setNavigationItemSelectedListener(this);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null || !firebaseAuth.getCurrentUser().isEmailVerified()) {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                } else {
                    setUserName(firebaseAuth.getCurrentUser().getEmail());
                }
            }
        };

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mUnitRecyclerViewAdapter = new UnitRecyclerViewAdapter(this);
        mRecyclerView.setAdapter(mUnitRecyclerViewAdapter);

        myViewModel.getmAllUnits().observe(this, new Observer<List<Unit>>() {
            @Override
            public void onChanged(@Nullable List<Unit> units) {
                mUnits = units;
                mUnitRecyclerViewAdapter.setUnits(units);
            }
        });
    }

    private void setUserName(String user) {
        mUserTextView.setText(user);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_delete_all) {
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    dataSnapshot.getRef().removeValue();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            myViewModel.deleteAllUnits();
        } else if (id == R.id.action_log_out) {
            mAuth.signOut();
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_calculator) {
            Intent intent = new Intent(this, QDCalculator.class);
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(int item) {
        Intent intent = new Intent(this, ViewUnitActivity.class);
        intent.putExtra(UNIT_NAME_EXTRA, mUnits.get(item).unitTitle);
        startActivity(intent);
    }


}
