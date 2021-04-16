package com.mediary;

import com.mediary.Models.Entities.ScheduleItemEntity;
import com.mediary.Models.Entities.ScheduleItemTypeEntity;
import com.mediary.Models.Entities.StatisticEntity;
import com.mediary.Models.Entities.StatisticTypeEntity;
import com.mediary.Models.Entities.TestResultEntity;
import com.mediary.Models.Entities.TestResultItemEntity;
import com.mediary.Models.Entities.TestTypeEntity;
import com.mediary.Models.Entities.UserEntity;
import com.mediary.Repositories.ScheduleItemRepository;
import com.mediary.Repositories.ScheduleItemTypeRepository;
import com.mediary.Repositories.StatisticRepository;
import com.mediary.Repositories.StatisticTypeRepository;
import com.mediary.Repositories.TestResultItemRepository;
import com.mediary.Repositories.TestResultRepository;
import com.mediary.Repositories.TestTypeRepository;
import com.mediary.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
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

    // initialize database?
    boolean initializeDatabase = false;

    @Override
    public void run(String... strings) {

        if (initializeDatabase == true) {
            UserEntity user = new UserEntity();
            user.setFullName("Jan Kowalski");
            user.setEmail("j.kowalski@mail.com");
            user.setGender("Male");
            user.setDateofbirth(Date.valueOf("1984-06-20"));
            user.setUsername("j.kowalski");
            user.setPassword("password123");

            userRepository.save(user);
            userRepository.flush();

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

            // Statistic types
            StatisticTypeEntity statisticType = new StatisticTypeEntity();
            statisticType.setName("Blood pressure");
            StatisticTypeEntity statisticType1 = new StatisticTypeEntity();
            statisticType1.setName("Pulse");
            StatisticTypeEntity statisticType2 = new StatisticTypeEntity();
            statisticType2.setName("Glucose level");
            StatisticTypeEntity statisticType3 = new StatisticTypeEntity();
            statisticType3.setName("Weight");

            statisticTypeRepository.save(statisticType);
            statisticTypeRepository.save(statisticType1);
            statisticTypeRepository.save(statisticType2);
            statisticTypeRepository.save(statisticType3);
            statisticTypeRepository.flush();

            // Test types
            TestTypeEntity testType = new TestTypeEntity();
            testType.setName("Other");
            testType.setParameters("");
            TestTypeEntity testType1 = new TestTypeEntity();
            testType1.setName("Blood count");
            testType1.setParameters("");
            TestTypeEntity testType2 = new TestTypeEntity();
            testType2.setName("Liver panel");
            testType2.setParameters("");
            TestTypeEntity testType3 = new TestTypeEntity();
            testType3.setName("Lipid profile test");
            testType3.setParameters("");
            TestTypeEntity testType4 = new TestTypeEntity();
            testType4.setName("Urine test");
            testType4.setParameters("");
            TestTypeEntity testType5 = new TestTypeEntity();
            testType5.setName("Pancreas profile test");
            testType5.setParameters("");
            TestTypeEntity testType6 = new TestTypeEntity();
            testType6.setName("Thyroid profile test");
            testType6.setParameters("");
            TestTypeEntity testType7 = new TestTypeEntity();
            testType7.setName("EKG");
            testType7.setParameters("");
            TestTypeEntity testType8 = new TestTypeEntity();
            testType8.setName("USG");
            testType8.setParameters("");
            TestTypeEntity testType9 = new TestTypeEntity();
            testType9.setName("Biopsy");
            testType9.setParameters("");
            TestTypeEntity testType10 = new TestTypeEntity();
            testType10.setName("X-ray");
            testType10.setParameters("");
            TestTypeEntity testType11 = new TestTypeEntity();
            testType11.setName("Gastroscopy");
            testType11.setParameters("");
            TestTypeEntity testType12 = new TestTypeEntity();
            testType12.setName("Cronoscopy");
            testType12.setParameters("");

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

            // Schedule items
            ScheduleItemEntity scheduleItem = new ScheduleItemEntity();
            scheduleItem.setTitle("Yearly check-up");
            scheduleItem.setDate(Date.valueOf("2021-06-17"));
            scheduleItem.setPlace("Medical Clinic");
            scheduleItem.setScheduleitemtypeByScheduleitemtypeid(scheduleItemType);
            scheduleItem.setUserByUserid(user);

            ScheduleItemEntity scheduleItem1 = new ScheduleItemEntity();
            scheduleItem1.setTitle("Knee rehabilitation");
            scheduleItem1.setDate(Date.valueOf("2021-03-15"));
            scheduleItem1.setPlace("Rehabilitation center");
            scheduleItem1.setScheduleitemtypeByScheduleitemtypeid(scheduleItemType2);
            scheduleItem1.setUserByUserid(user);

            ScheduleItemEntity scheduleItem2 = new ScheduleItemEntity();
            scheduleItem2.setTitle("Knee rehabilitation");
            scheduleItem2.setDate(Date.valueOf("2021-04-19"));
            scheduleItem2.setPlace("Rehabilitation center");
            scheduleItem2.setScheduleitemtypeByScheduleitemtypeid(scheduleItemType2);
            scheduleItem2.setUserByUserid(user);

            ScheduleItemEntity scheduleItem3 = new ScheduleItemEntity();
            scheduleItem3.setTitle("Knee rehabilitation");
            scheduleItem3.setDate(Date.valueOf("2021-05-17"));
            scheduleItem3.setPlace("Rehabilitation center");
            scheduleItem3.setScheduleitemtypeByScheduleitemtypeid(scheduleItemType2);
            scheduleItem3.setUserByUserid(user);

            scheduleItemRepository.save(scheduleItem);
            scheduleItemRepository.save(scheduleItem1);
            scheduleItemRepository.save(scheduleItem2);
            scheduleItemRepository.save(scheduleItem3);
            scheduleItemTypeRepository.flush();

            // Statistics
            StatisticEntity statistic = new StatisticEntity();
            statistic.setValue("120");
            statistic.setDate(Date.valueOf("2021-04-12"));
            statistic.setStatistictypeByStatistictypeid(statisticType2);
            statistic.setUserByUserid(user);

            StatisticEntity statistic1 = new StatisticEntity();
            statistic1.setValue("110");
            statistic1.setDate(Date.valueOf("2021-04-13"));
            statistic1.setStatistictypeByStatistictypeid(statisticType2);
            statistic1.setUserByUserid(user);

            StatisticEntity statistic2 = new StatisticEntity();
            statistic2.setValue("130");
            statistic2.setDate(Date.valueOf("2021-04-14"));
            statistic2.setStatistictypeByStatistictypeid(statisticType2);
            statistic2.setUserByUserid(user);

            StatisticEntity statistic3 = new StatisticEntity();
            statistic3.setValue("122");
            statistic3.setDate(Date.valueOf("2021-04-15"));
            statistic3.setStatistictypeByStatistictypeid(statisticType2);
            statistic3.setUserByUserid(user);

            statisticRepository.save(statistic);
            statisticRepository.save(statistic1);
            statisticRepository.save(statistic2);
            statisticRepository.save(statistic3);
            statisticRepository.flush();

            // Test results
            TestResultEntity testResult = new TestResultEntity();
            testResult.setTitle("Annual blood test");
            testResult.setUserByUserid(user);
            testResult.setDateofthetest(Date.valueOf("2021-03-12"));
            testResult.setTesttypeByTesttypeid(testType1);

            TestResultEntity testResult1 = new TestResultEntity();
            testResult1.setTitle("Cholesterol test");
            testResult1.setUserByUserid(user);
            testResult1.setDateofthetest(Date.valueOf("2021-04-02"));
            testResult1.setTesttypeByTesttypeid(testType3);

            testResultRepository.save(testResult);
            testResultRepository.save(testResult1);
            testResultRepository.flush();

            // Test result items
            TestResultItemEntity testResultItem = new TestResultItemEntity();
            testResultItem.setName("Leukocytes");
            testResultItem.setUnit("10^9/L");
            testResultItem.setValue("5,2");
            testResultItem.setTestresultByTestresultid(testResult);

            TestResultItemEntity testResultItem1 = new TestResultItemEntity();
            testResultItem1.setName("Erythrocytes");
            testResultItem1.setUnit("10^6/uL");
            testResultItem1.setValue("4,8");
            testResultItem1.setTestresultByTestresultid(testResult);

            TestResultItemEntity testResultItem2 = new TestResultItemEntity();
            testResultItem2.setName("Platelet count");
            testResultItem2.setUnit("10^3/uL");
            testResultItem2.setValue("220");
            testResultItem2.setTestresultByTestresultid(testResult);

            testResultItemRepository.save(testResultItem);
            testResultItemRepository.save(testResultItem1);
            testResultItemRepository.save(testResultItem2);
            testResultItemRepository.flush();

            TestResultItemEntity testResultItem3 = new TestResultItemEntity();
            testResultItem3.setName("Total cholesterol");
            testResultItem3.setUnit("mg/dL");
            testResultItem3.setValue("190");
            testResultItem3.setTestresultByTestresultid(testResult1);

            TestResultItemEntity testResultItem4 = new TestResultItemEntity();
            testResultItem4.setName("LDL cholesterol");
            testResultItem4.setUnit("mg/dL");
            testResultItem4.setValue("110");
            testResultItem4.setTestresultByTestresultid(testResult1);

            TestResultItemEntity testResultItem5 = new TestResultItemEntity();
            testResultItem5.setName("HDL cholesterol");
            testResultItem5.setUnit("mg/dL");
            testResultItem5.setValue("45");
            testResultItem5.setTestresultByTestresultid(testResult1);

            TestResultItemEntity testResultItem6 = new TestResultItemEntity();
            testResultItem6.setName("Tryglycerides");
            testResultItem6.setUnit("mg/dL");
            testResultItem6.setValue("140");
            testResultItem6.setTestresultByTestresultid(testResult1);

            testResultItemRepository.save(testResultItem3);
            testResultItemRepository.save(testResultItem4);
            testResultItemRepository.save(testResultItem5);
            testResultItemRepository.save(testResultItem6);
            testResultItemRepository.flush();

        }

    }

}