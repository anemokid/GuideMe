package com.nyc.guideme;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.nyc.guideme.models.JobModels;

import java.io.IOException;
import java.util.List;

public class JobDetailsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private JobModels job;
    private TextView businessTitle, civicTitle, divisionWorkUnit,
            salaryFrom, salaryTo, salaryFrequency, jobCategory,
            workLocation, fpIndicator, jobDescription, minimumQualification,
            residencyReq, postUntil, postingDate, preferredSkills;

    private GoogleMap mMap;
    private Address location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);

        Intent intent = getIntent();
        if (intent.hasExtra("jobDetails")) {
            job = new Gson().fromJson(intent.getStringExtra("jobDetails"), JobModels.class);
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_details);
        mapFragment.getMapAsync(this);

        initViews();
        setViews();
    }

    private void setViews() {
        businessTitle.setText(job.getBusiness_title());
        civicTitle.setText(job.getCivil_service_title());
        divisionWorkUnit.setText(job.getDivision_work_unit());
        salaryFrom.setText(job.getSalary_range_from());
        salaryTo.setText(job.getSalary_range_to());
        salaryFrequency.setText(job.getSalary_frequency());
        jobCategory.setText(job.getJob_category());
        workLocation.setText(job.getWork_location());
        fpIndicator.setText(job.getFull_time_part_time_indicator());
        minimumQualification.setText(job.getMinimum_qual_requirements());
        residencyReq.setText(job.getResidency_requirement());
        postUntil.setText(job.getPost_until());
        postingDate.setText(job.getPosting_date());
        preferredSkills.setText(job.getPreferred_skills());
    }

    private void initViews() {
        businessTitle = findViewById(R.id.business_title_details);
        civicTitle = findViewById(R.id.civic_title_details);
        divisionWorkUnit = findViewById(R.id.division_work_unit_details);
        salaryFrom = findViewById(R.id.salary_from_details);
        salaryTo = findViewById(R.id.salary_to_details);
        salaryFrequency = findViewById(R.id.salary_frequency_detail);
        jobCategory = findViewById(R.id.job_category_details);
        workLocation = findViewById(R.id.work_location_details);
        fpIndicator = findViewById(R.id.full_part_indicator_details);
        jobDescription = findViewById(R.id.job_description_details);
        minimumQualification = findViewById(R.id.minimum_req_details);
        residencyReq = findViewById(R.id.residency_req_details);
        postUntil = findViewById(R.id.post_until_details);
        postingDate = findViewById(R.id.post_date_details);
        preferredSkills = findViewById(R.id.prefered_skills_details);
    }

    public Address getLocationFromAddress(String strAddress) throws IOException {

        Geocoder coder = new Geocoder(this);
        List<Address> address;

        address = coder.getFromLocationName(strAddress, 5);
        if (address == null) {
            return null;
        }
        Address location = address.get(0);
        location.getLatitude();
        location.getLongitude();

        return location;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        try {
            location = getLocationFromAddress(job.getWork_location());
            LatLng jobMarker = new LatLng(location.getLatitude(),location.getLongitude());
            mMap.addMarker(new MarkerOptions().position(jobMarker).title(job.getAgency()));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(jobMarker));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}