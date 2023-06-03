package com.example.mindful.service.appointment;

import com.example.mindful.service.appointment.data.Allergies;
import com.example.mindful.service.appointment.data.CurrentSymptom;
import com.example.mindful.service.appointment.data.EmergencyContact;
import com.example.mindful.service.appointment.data.LifestyleHabit;
import com.example.mindful.service.appointment.data.MedicalCondition;
import com.example.mindful.service.appointment.data.Medications;
import com.example.mindful.service.appointment.data.PersonalInfo;
import com.example.mindful.service.appointment.data.SurgicalHistory;


import java.time.LocalDate;

public class AppointmentDataManager {

    private PersonalInfo personalInfo;
    private MedicalCondition medicalCondition;
    private Medications medications;
    private SurgicalHistory surgicalHistory;
    private LifestyleHabit lifestyleHabit;
    private Allergies allergies;
    private EmergencyContact emergencyContact;
    private CurrentSymptom currentSymptom;
}
