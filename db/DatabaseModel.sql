DROP SCHEMA public CASCADE;

CREATE SCHEMA public;

CREATE TABLE Users(
    ID serial PRIMARY KEY,
    UID VARCHAR (50) NOT NULL,
    FullName VARCHAR (40) NOT NULL,
    Gender VARCHAR(6) NOT NULL DEFAULT 'Male',
    DateOfBirth DATE NOT NULL DEFAULT '1970-01-01',
    Weight NUMERIC (4,1) DEFAULT 0.0
);

CREATE TABLE StatisticType (
    ID serial PRIMARY KEY,
    Name VARCHAR (40) NOT NULL
);

CREATE TABLE Statistic (
    ID serial PRIMARY KEY,
    Value VARCHAR (50),
    Date DATE NOT NULL DEFAULT CURRENT_DATE,
    StatisticTypeID INTEGER NOT NULL,
    UserID INTEGER NOT NULL,
    CONSTRAINT FK_StatisticType
        FOREIGN KEY (StatisticTypeID) REFERENCES StatisticType (ID),
    CONSTRAINT FK_User
        FOREIGN KEY (UserID) REFERENCES Users (ID)
);

CREATE TABLE ScheduleItemType (
    ID serial PRIMARY KEY,
    Name VARCHAR (40) NOT NULL
);

CREATE TABLE ScheduleItem (
    ID serial PRIMARY KEY,
    Title VARCHAR (30) NOT NULL,
    Date DATE NOT NULL DEFAULT CURRENT_DATE,
    Place VARCHAR (30) NOT NULL,
    Address VARCHAR (50),
    Note VARCHAR (200),
    UserID INTEGER NOT NULL,
    ScheduleItemTypeID INTEGER NOT NULL,
    CONSTRAINT FK_User
        FOREIGN KEY (UserID) REFERENCES Users (ID),
    CONSTRAINT FK_ScheduleItemType
        FOREIGN KEY (ScheduleItemTypeID) REFERENCES ScheduleItemType (ID)
);

CREATE TABLE TestType (
    ID serial PRIMARY KEY,
    Name VARCHAR (40) NOT NULL,
    Parameters json NOT NULL
);

CREATE TABLE MedicalRecord (
    ID serial PRIMARY KEY,
    Title VARCHAR (30) NOT NULL,
    Note VARCHAR (200),
    DateOfTheTest DATE NOT NULL DEFAULT CURRENT_DATE,
    TestTypeID INTEGER NOT NULL,
    UserID INTEGER NOT NULL,
    CONSTRAINT FK_TestType
        FOREIGN KEY (TestTypeID) REFERENCES TestType (ID),
    CONSTRAINT FK_User
        FOREIGN KEY (UserID) REFERENCES Users (ID)
);

CREATE TABLE TestItem (
    ID serial PRIMARY KEY,
    Name VARCHAR (40) NOT NULL,
    Value VARCHAR (50),
    Unit VARCHAR (10),
    MedicalRecordID INTEGER NOT NULL,
    CONSTRAINT FK_MedicalRecord
        FOREIGN KEY (MedicalRecordID) REFERENCES MedicalRecord (ID)
);

CREATE TABLE Files (
    ID serial PRIMARY KEY,
    UUID VARCHAR (36) NOT NULL,
    OriginalName VARCHAR (50) NOT NULL,
    URL VARCHAR (200) NOT NULL,
    MedicalRecordID INTEGER NOT NULL,
    CONSTRAINT FK_MedicalRecord
        FOREIGN KEY (MedicalRecordID) REFERENCES MedicalRecord (ID)
)

