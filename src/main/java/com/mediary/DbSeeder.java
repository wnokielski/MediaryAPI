package com.mediary;

import com.mediary.Models.Entities.ScheduleItemEntity;
import com.mediary.Models.Entities.ScheduleItemTypeEntity;
import com.mediary.Models.Entities.StatisticEntity;
import com.mediary.Models.Entities.StatisticTypeEntity;
import com.mediary.Models.Entities.TestParameterEntity;
import com.mediary.Models.Entities.TestResultEntity;
import com.mediary.Models.Entities.TestResultItemEntity;
import com.mediary.Models.Entities.TestTypeEntity;
import com.mediary.Models.Entities.UserEntity;
import com.mediary.Models.Enums.Gender;
import com.mediary.Repositories.ScheduleItemRepository;
import com.mediary.Repositories.ScheduleItemTypeRepository;
import com.mediary.Repositories.StatisticRepository;
import com.mediary.Repositories.StatisticTypeRepository;
import com.mediary.Repositories.TestParameterRepository;
import com.mediary.Repositories.TestResultItemRepository;
import com.mediary.Repositories.TestResultRepository;
import com.mediary.Repositories.TestTypeRepository;
import com.mediary.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
public class DbSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ScheduleItemTypeRepository scheduleItemTypeRepository;

    @Autowired
    private StatisticTypeRepository statisticTypeRepository;

    @Autowired
    private TestTypeRepository testTypeRepository;

    @Autowired
    private ScheduleItemRepository scheduleItemRepository;

    @Autowired
    private StatisticRepository statisticRepository;

    @Autowired
    private TestResultRepository testResultRepository;

    @Autowired
    private TestResultItemRepository testResultItemRepository;

    @Autowired
    private TestParameterRepository testParameterRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    // initialize database?
    boolean initializeDatabase = false;

    @Override
    public void run(String... strings) {

        if (initializeDatabase == true) {
            UserEntity user = new UserEntity();
            user.setFullName("Jan Kowalski");
            user.setEmail("j.kowalski@mail.com");
            user.setGender(Gender.MALE);
            user.setDateOfBirth(Date.valueOf("1984-06-20"));
            user.setPassword(passwordEncoder.encode("password123"));

            userRepository.save(user);
            userRepository.flush();

            // Schedule item types
            // Scedule items
            {
                // Schedule item types
                ScheduleItemTypeEntity scheduleItemType = new ScheduleItemTypeEntity();
                scheduleItemType.setName("Consultation");
                ScheduleItemTypeEntity scheduleItemType1 = new ScheduleItemTypeEntity();
                scheduleItemType1.setName("Examination");
                ScheduleItemTypeEntity scheduleItemType2 = new ScheduleItemTypeEntity();
                scheduleItemType2.setName("Treatment");
                ScheduleItemTypeEntity scheduleItemType3 = new ScheduleItemTypeEntity();
                scheduleItemType3.setName("Other");

                scheduleItemTypeRepository.save(scheduleItemType);
                scheduleItemTypeRepository.save(scheduleItemType1);
                scheduleItemTypeRepository.save(scheduleItemType2);
                scheduleItemTypeRepository.save(scheduleItemType3);
                scheduleItemTypeRepository.flush();

                // Schedule items
                ScheduleItemEntity scheduleItem = new ScheduleItemEntity();
                scheduleItem.setTitle("Yearly check-up");
                scheduleItem.setDate(Date.valueOf("2021-06-17"));
                scheduleItem.setPlace("Medical Clinic");
                scheduleItem.setScheduleItemTypeById(scheduleItemType);
                scheduleItem.setUserById(user);

                ScheduleItemEntity scheduleItem1 = new ScheduleItemEntity();
                scheduleItem1.setTitle("Knee rehabilitation");
                scheduleItem1.setDate(Date.valueOf("2021-03-15"));
                scheduleItem1.setPlace("Rehabilitation center");
                scheduleItem1.setScheduleItemTypeById(scheduleItemType2);
                scheduleItem1.setUserById(user);

                ScheduleItemEntity scheduleItem2 = new ScheduleItemEntity();
                scheduleItem2.setTitle("Knee rehabilitation");
                scheduleItem2.setDate(Date.valueOf("2021-04-19"));
                scheduleItem2.setPlace("Rehabilitation center");
                scheduleItem2.setScheduleItemTypeById(scheduleItemType2);
                scheduleItem2.setUserById(user);

                ScheduleItemEntity scheduleItem3 = new ScheduleItemEntity();
                scheduleItem3.setTitle("Knee rehabilitation");
                scheduleItem3.setDate(Date.valueOf("2021-05-17"));
                scheduleItem3.setPlace("Rehabilitation center");
                scheduleItem3.setScheduleItemTypeById(scheduleItemType2);
                scheduleItem3.setUserById(user);

                scheduleItemRepository.save(scheduleItem);
                scheduleItemRepository.save(scheduleItem1);
                scheduleItemRepository.save(scheduleItem2);
                scheduleItemRepository.save(scheduleItem3);
                scheduleItemTypeRepository.flush();
            }

            // Statistic types
            // Statistics
            {
                StatisticTypeEntity statisticType = new StatisticTypeEntity();
                statisticType.setName("Blood pressure");
                statisticType.setUnit("mmHg");
                StatisticTypeEntity statisticType1 = new StatisticTypeEntity();
                statisticType1.setName("Pulse");
                statisticType1.setUnit("/min");
                StatisticTypeEntity statisticType2 = new StatisticTypeEntity();
                statisticType2.setName("Glucose level");
                statisticType2.setUnit("md/dL");
                StatisticTypeEntity statisticType3 = new StatisticTypeEntity();
                statisticType3.setName("Weight");
                statisticType3.setUnit("kg");

                statisticTypeRepository.save(statisticType);
                statisticTypeRepository.save(statisticType1);
                statisticTypeRepository.save(statisticType2);
                statisticTypeRepository.save(statisticType3);
                statisticTypeRepository.flush();

                // Statistics
                StatisticEntity statistic = new StatisticEntity();
                statistic.setValue("120");
                statistic.setDate(Date.valueOf("2021-04-12"));
                statistic.setStatisticTypeById(statisticType2);
                statistic.setUserById(user);

                StatisticEntity statistic1 = new StatisticEntity();
                statistic1.setValue("110");
                statistic1.setDate(Date.valueOf("2021-04-13"));
                statistic1.setStatisticTypeById(statisticType2);
                statistic1.setUserById(user);

                StatisticEntity statistic2 = new StatisticEntity();
                statistic2.setValue("130");
                statistic2.setDate(Date.valueOf("2021-04-14"));
                statistic2.setStatisticTypeById(statisticType2);
                statistic2.setUserById(user);

                StatisticEntity statistic3 = new StatisticEntity();
                statistic3.setValue("122");
                statistic3.setDate(Date.valueOf("2021-04-15"));
                statistic3.setStatisticTypeById(statisticType2);
                statistic3.setUserById(user);

                statisticRepository.save(statistic);
                statisticRepository.save(statistic1);
                statisticRepository.save(statistic2);
                statisticRepository.save(statistic3);
                statisticRepository.flush();
            }

            // Test types
            TestTypeEntity testType = new TestTypeEntity();
            testType.setName("Other");
            TestTypeEntity testType1 = new TestTypeEntity();
            testType1.setName("Blood count");
            TestTypeEntity testType2 = new TestTypeEntity();
            testType2.setName("Liver panel");
            TestTypeEntity testType3 = new TestTypeEntity();
            testType3.setName("Lipid profile test");
            TestTypeEntity testType4 = new TestTypeEntity();
            testType4.setName("Urine test");
            TestTypeEntity testType5 = new TestTypeEntity();
            testType5.setName("Pancreas profile test");
            TestTypeEntity testType6 = new TestTypeEntity();
            testType6.setName("Thyroid profile test");
            TestTypeEntity testType7 = new TestTypeEntity();
            testType7.setName("EKG");
            TestTypeEntity testType8 = new TestTypeEntity();
            testType8.setName("USG");
            TestTypeEntity testType9 = new TestTypeEntity();
            testType9.setName("Biopsy");
            TestTypeEntity testType10 = new TestTypeEntity();
            testType10.setName("X-ray");
            TestTypeEntity testType11 = new TestTypeEntity();
            testType11.setName("Gastroscopy");
            TestTypeEntity testType12 = new TestTypeEntity();
            testType12.setName("Cronoscopy");

            testTypeRepository.save(testType);
            testTypeRepository.save(testType1);
            testTypeRepository.save(testType2);
            testTypeRepository.save(testType3);
            testTypeRepository.save(testType4);
            testTypeRepository.save(testType5);
            testTypeRepository.save(testType6);
            testTypeRepository.save(testType7);
            testTypeRepository.save(testType8);
            testTypeRepository.save(testType9);
            testTypeRepository.save(testType10);
            testTypeRepository.save(testType11);
            testTypeRepository.save(testType12);
            testTypeRepository.flush();

            // Test parameters
            // Blood count
            {
                TestParameterEntity testParameter = new TestParameterEntity();
                testParameter.setName("Leukocytes");
                testParameter.setUnit("10^9/L");
                testParameter.setTestTypeById(testType1);
                TestParameterEntity testParameter1 = new TestParameterEntity();
                testParameter1.setName("Erythrocytes");
                testParameter1.setUnit("10^6/μl");
                testParameter1.setTestTypeById(testType1);
                TestParameterEntity testParameter2 = new TestParameterEntity();
                testParameter2.setName("Hemoglobin");
                testParameter2.setUnit("g/dl");
                testParameter2.setTestTypeById(testType1);
                TestParameterEntity testParameter3 = new TestParameterEntity();
                testParameter3.setName("Hematocrit");
                testParameter3.setUnit("%");
                testParameter3.setTestTypeById(testType1);
                TestParameterEntity testParameter4 = new TestParameterEntity();
                testParameter4.setName("MCV");
                testParameter4.setUnit("fl");
                testParameter4.setTestTypeById(testType1);
                TestParameterEntity testParameter5 = new TestParameterEntity();
                testParameter5.setName("MCH");
                testParameter5.setUnit("pg");
                testParameter5.setTestTypeById(testType1);
                TestParameterEntity testParameter6 = new TestParameterEntity();
                testParameter6.setName("MCHC");
                testParameter6.setUnit("g/dl");
                testParameter6.setTestTypeById(testType1);
                TestParameterEntity testParameter7 = new TestParameterEntity();
                testParameter7.setName("Platelets");
                testParameter7.setUnit("10^3/μl");
                testParameter7.setTestTypeById(testType1);
                TestParameterEntity testParameter8 = new TestParameterEntity();
                testParameter8.setName("RDW-CV");
                testParameter8.setUnit("%");
                testParameter8.setTestTypeById(testType1);
                TestParameterEntity testParameter9 = new TestParameterEntity();
                testParameter9.setName("PDW");
                testParameter9.setUnit("fl");
                testParameter9.setTestTypeById(testType1);
                TestParameterEntity testParameter10 = new TestParameterEntity();
                testParameter10.setName("MPV");
                testParameter10.setUnit("fl");
                testParameter10.setTestTypeById(testType1);
                TestParameterEntity testParameter11 = new TestParameterEntity();
                testParameter11.setName("P-LCR");
                testParameter11.setUnit("%");
                testParameter11.setTestTypeById(testType1);
                TestParameterEntity testParameter12 = new TestParameterEntity();
                testParameter12.setName("Absolute neutrophil count");
                testParameter12.setUnit("10^3/μl");
                testParameter12.setTestTypeById(testType1);
                TestParameterEntity testParameter13 = new TestParameterEntity();
                testParameter13.setName("Absolute lymphocyte count");
                testParameter13.setUnit("10^3/μl");
                testParameter13.setTestTypeById(testType1);
                TestParameterEntity testParameter14 = new TestParameterEntity();
                testParameter14.setName("Absolute monocyte count");
                testParameter14.setUnit("10^3/μl");
                testParameter14.setTestTypeById(testType1);
                TestParameterEntity testParameter15 = new TestParameterEntity();
                testParameter15.setName("Absolute eosinophil count");
                testParameter15.setUnit("10^3/μl");
                testParameter15.setTestTypeById(testType1);
                TestParameterEntity testParameter16 = new TestParameterEntity();
                testParameter16.setName("Absolute basophil count");
                testParameter16.setUnit("10^3/μl");
                testParameter16.setTestTypeById(testType1);
                TestParameterEntity testParameter17 = new TestParameterEntity();
                testParameter17.setName("% neutrophils");
                testParameter17.setUnit("%");
                testParameter17.setTestTypeById(testType1);
                TestParameterEntity testParameter18 = new TestParameterEntity();
                testParameter18.setName("% monocytes");
                testParameter18.setUnit("%");
                testParameter18.setTestTypeById(testType1);
                TestParameterEntity testParameter19 = new TestParameterEntity();
                testParameter19.setName("% lymphocytes");
                testParameter19.setUnit("%");
                testParameter19.setTestTypeById(testType1);
                TestParameterEntity testParameter20 = new TestParameterEntity();
                testParameter20.setName("% eosinophils");
                testParameter20.setUnit("%");
                testParameter20.setTestTypeById(testType1);
                TestParameterEntity testParameter21 = new TestParameterEntity();
                testParameter21.setName("% basophils");
                testParameter21.setUnit("%");
                testParameter21.setTestTypeById(testType1);

                testParameterRepository.save(testParameter);
                testParameterRepository.save(testParameter1);
                testParameterRepository.save(testParameter2);
                testParameterRepository.save(testParameter3);
                testParameterRepository.save(testParameter4);
                testParameterRepository.save(testParameter5);
                testParameterRepository.save(testParameter6);
                testParameterRepository.save(testParameter7);
                testParameterRepository.save(testParameter8);
                testParameterRepository.save(testParameter9);
                testParameterRepository.save(testParameter10);
                testParameterRepository.save(testParameter11);
                testParameterRepository.save(testParameter12);
                testParameterRepository.save(testParameter13);
                testParameterRepository.save(testParameter14);
                testParameterRepository.save(testParameter15);
                testParameterRepository.save(testParameter16);
                testParameterRepository.save(testParameter17);
                testParameterRepository.save(testParameter18);
                testParameterRepository.save(testParameter19);
                testParameterRepository.save(testParameter20);
                testParameterRepository.save(testParameter21);
                testParameterRepository.flush();
            }
            // Liver Panel
            {
                TestParameterEntity testParameter = new TestParameterEntity();
                testParameter.setName("AST");
                testParameter.setUnit("U/L");
                testParameter.setTestTypeById(testType2);
                TestParameterEntity testParameter1 = new TestParameterEntity();
                testParameter1.setName("ALT");
                testParameter1.setUnit("U/L");
                testParameter1.setTestTypeById(testType2);
                TestParameterEntity testParameter2 = new TestParameterEntity();
                testParameter2.setName("ALP");
                testParameter2.setUnit("U/L");
                testParameter2.setTestTypeById(testType2);
                TestParameterEntity testParameter3 = new TestParameterEntity();
                testParameter3.setName("Albumin");
                testParameter3.setUnit("g/dl");
                testParameter3.setTestTypeById(testType2);
                TestParameterEntity testParameter4 = new TestParameterEntity();
                testParameter4.setName("Total protein");
                testParameter4.setUnit("g/dl");
                testParameter4.setTestTypeById(testType2);
                TestParameterEntity testParameter5 = new TestParameterEntity();
                testParameter5.setName("Bilirubin");
                testParameter5.setUnit("mg/dl");
                testParameter5.setTestTypeById(testType2);
                TestParameterEntity testParameter6 = new TestParameterEntity();
                testParameter6.setName("GGTP");
                testParameter6.setUnit("U/L");
                testParameter6.setTestTypeById(testType2);
                TestParameterEntity testParameter7 = new TestParameterEntity();
                testParameter7.setName("LD");
                testParameter7.setUnit("U/L");
                testParameter7.setTestTypeById(testType2);

                testParameterRepository.save(testParameter);
                testParameterRepository.save(testParameter1);
                testParameterRepository.save(testParameter2);
                testParameterRepository.save(testParameter3);
                testParameterRepository.save(testParameter4);
                testParameterRepository.save(testParameter5);
                testParameterRepository.save(testParameter6);
                testParameterRepository.save(testParameter7);
                testParameterRepository.flush();
            }
            // Lipid profile Test
            {
                TestParameterEntity testParameter = new TestParameterEntity();
                testParameter.setName("Total cholesterol");
                testParameter.setUnit("mg/dL");
                testParameter.setTestTypeById(testType3);
                TestParameterEntity testParameter1 = new TestParameterEntity();
                testParameter1.setName("LDL Cholesterol");
                testParameter1.setUnit("mg/dL");
                testParameter1.setTestTypeById(testType3);
                TestParameterEntity testParameter2 = new TestParameterEntity();
                testParameter2.setName("HDL Cholesterol");
                testParameter2.setUnit("mg/dL");
                testParameter2.setTestTypeById(testType3);
                TestParameterEntity testParameter3 = new TestParameterEntity();
                testParameter3.setName("Tryglycerides");
                testParameter3.setUnit("mg/dL");
                testParameter3.setTestTypeById(testType3);

                testParameterRepository.save(testParameter);
                testParameterRepository.save(testParameter1);
                testParameterRepository.save(testParameter2);
                testParameterRepository.save(testParameter3);
                testParameterRepository.flush();
            }
            // Urine test
            {
                TestParameterEntity testParameter = new TestParameterEntity();
                testParameter.setName("Color");
                testParameter.setTestTypeById(testType4);
                TestParameterEntity testParameter1 = new TestParameterEntity();
                testParameter1.setName("Appearance");
                testParameter1.setTestTypeById(testType4);
                TestParameterEntity testParameter2 = new TestParameterEntity();
                testParameter2.setName("Specific Gravity");
                testParameter2.setTestTypeById(testType4);
                TestParameterEntity testParameter3 = new TestParameterEntity();
                testParameter3.setName("pH");
                testParameter3.setTestTypeById(testType4);
                TestParameterEntity testParameter4 = new TestParameterEntity();
                testParameter4.setName("Glucose");
                testParameter4.setUnit("mg/dL");
                testParameter4.setTestTypeById(testType4);
                TestParameterEntity testParameter5 = new TestParameterEntity();
                testParameter5.setName("Ketones");
                testParameter5.setUnit("mg/dL");
                testParameter5.setTestTypeById(testType4);
                TestParameterEntity testParameter6 = new TestParameterEntity();
                testParameter6.setName("Urobilinogene");
                testParameter6.setUnit("EU/dL");
                testParameter6.setTestTypeById(testType4);
                TestParameterEntity testParameter7 = new TestParameterEntity();
                testParameter7.setName("Bilirubin");
                testParameter7.setTestTypeById(testType4);
                TestParameterEntity testParameter8 = new TestParameterEntity();
                testParameter8.setName("Protein");
                testParameter8.setUnit("mg/dL");
                testParameter8.setTestTypeById(testType4);
                TestParameterEntity testParameter9 = new TestParameterEntity();
                testParameter9.setName("Nitrite");
                testParameter9.setTestTypeById(testType4);
                TestParameterEntity testParameter10 = new TestParameterEntity();
                testParameter10.setName("Erythrocytes");
                testParameter10.setTestTypeById(testType4);
                TestParameterEntity testParameter11 = new TestParameterEntity();
                testParameter11.setName("Leukocytes");
                testParameter11.setTestTypeById(testType4);

                testParameterRepository.save(testParameter);
                testParameterRepository.save(testParameter1);
                testParameterRepository.save(testParameter2);
                testParameterRepository.save(testParameter3);
                testParameterRepository.save(testParameter4);
                testParameterRepository.save(testParameter5);
                testParameterRepository.save(testParameter6);
                testParameterRepository.save(testParameter7);
                testParameterRepository.save(testParameter8);
                testParameterRepository.save(testParameter9);
                testParameterRepository.save(testParameter10);
                testParameterRepository.save(testParameter11);
                testParameterRepository.flush();
            }
            // Pancreas profile test
            {
                TestParameterEntity testParameter = new TestParameterEntity();
                testParameter.setName("Serum Amylase");
                testParameter.setUnit("U/L");
                testParameter.setTestTypeById(testType5);
                TestParameterEntity testParameter1 = new TestParameterEntity();
                testParameter1.setName("Serum Lipase");
                testParameter1.setUnit("U/L");
                testParameter1.setTestTypeById(testType5);

                testParameterRepository.save(testParameter);
                testParameterRepository.save(testParameter1);
                testParameterRepository.flush();
            }
            // Thryroid profile test
            {
                TestParameterEntity testParameter = new TestParameterEntity();
                testParameter.setName("T3");
                testParameter.setUnit("ng/dL");
                testParameter.setTestTypeById(testType6);
                TestParameterEntity testParameter1 = new TestParameterEntity();
                testParameter1.setName("T4");
                testParameter1.setUnit("μg/dL");
                testParameter1.setTestTypeById(testType6);
                TestParameterEntity testParameter2 = new TestParameterEntity();
                testParameter2.setName("TSH");
                testParameter2.setUnit("µIU/ml");
                testParameter2.setTestTypeById(testType6);
                TestParameterEntity testParameter3 = new TestParameterEntity();
                testParameter3.setName("FT3");
                testParameter3.setUnit("pg/ml");
                testParameter3.setTestTypeById(testType6);
                TestParameterEntity testParameter4 = new TestParameterEntity();
                testParameter4.setName("FT4");
                testParameter4.setUnit("ng/dL");
                testParameter4.setTestTypeById(testType6);
                TestParameterEntity testParameter5 = new TestParameterEntity();
                testParameter5.setName("ATG");
                testParameter5.setUnit("IU/ml");
                testParameter5.setTestTypeById(testType6);
                TestParameterEntity testParameter6 = new TestParameterEntity();
                testParameter6.setName("Anti-TPO");
                testParameter6.setUnit("IU/ml");
                testParameter6.setTestTypeById(testType6);

                testParameterRepository.save(testParameter);
                testParameterRepository.save(testParameter1);
                testParameterRepository.save(testParameter2);
                testParameterRepository.save(testParameter3);
                testParameterRepository.save(testParameter4);
                testParameterRepository.save(testParameter5);
                testParameterRepository.save(testParameter6);
                testParameterRepository.flush();
            }

            // Test results
            // Test result items
            {
                // Test results
                TestResultEntity testResult = new TestResultEntity();
                testResult.setTitle("Annual blood test");
                testResult.setUserById(user);
                testResult.setDateOfTheTest(Date.valueOf("2021-03-12"));
                testResult.setTestTypeById(testType1);

                TestResultEntity testResult1 = new TestResultEntity();
                testResult1.setTitle("Cholesterol test");
                testResult1.setUserById(user);
                testResult1.setDateOfTheTest(Date.valueOf("2021-04-02"));
                testResult1.setTestTypeById(testType3);

                testResultRepository.save(testResult);
                testResultRepository.save(testResult1);
                testResultRepository.flush();

                // Test result items
                TestResultItemEntity testResultItem = new TestResultItemEntity();
                testResultItem.setName("Leukocytes");
                testResultItem.setUnit("10^9/L");
                testResultItem.setValue("5,2");
                testResultItem.setTestResultById(testResult);

                TestResultItemEntity testResultItem1 = new TestResultItemEntity();
                testResultItem1.setName("Erythrocytes");
                testResultItem1.setUnit("10^6/uL");
                testResultItem1.setValue("4,8");
                testResultItem1.setTestResultById(testResult);

                TestResultItemEntity testResultItem2 = new TestResultItemEntity();
                testResultItem2.setName("Platelet count");
                testResultItem2.setUnit("10^3/uL");
                testResultItem2.setValue("220");
                testResultItem2.setTestResultById(testResult);

                testResultItemRepository.save(testResultItem);
                testResultItemRepository.save(testResultItem1);
                testResultItemRepository.save(testResultItem2);
                testResultItemRepository.flush();

                TestResultItemEntity testResultItem3 = new TestResultItemEntity();
                testResultItem3.setName("Total cholesterol");
                testResultItem3.setUnit("mg/dL");
                testResultItem3.setValue("190");
                testResultItem3.setTestResultById(testResult1);

                TestResultItemEntity testResultItem4 = new TestResultItemEntity();
                testResultItem4.setName("LDL cholesterol");
                testResultItem4.setUnit("mg/dL");
                testResultItem4.setValue("110");
                testResultItem4.setTestResultById(testResult1);

                TestResultItemEntity testResultItem5 = new TestResultItemEntity();
                testResultItem5.setName("HDL cholesterol");
                testResultItem5.setUnit("mg/dL");
                testResultItem5.setValue("45");
                testResultItem5.setTestResultById(testResult1);

                TestResultItemEntity testResultItem6 = new TestResultItemEntity();
                testResultItem6.setName("Tryglycerides");
                testResultItem6.setUnit("mg/dL");
                testResultItem6.setValue("140");
                testResultItem6.setTestResultById(testResult1);

                testResultItemRepository.save(testResultItem3);
                testResultItemRepository.save(testResultItem4);
                testResultItemRepository.save(testResultItem5);
                testResultItemRepository.save(testResultItem6);
                testResultItemRepository.flush();
            }

        }

    }

}