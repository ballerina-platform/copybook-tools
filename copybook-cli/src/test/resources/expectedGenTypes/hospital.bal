import ballerina/constraint;

@constraint:String {maxLength: 20}
public type AlphaNumeric20 string;

@constraint:String {maxLength: 30}
public type AlphaNumeric30 string;

@constraint:String {maxLength: 10}
public type AlphaNumeric10 string;

@constraint:String {maxLength: 2}
public type AlphaNumeric2 string;

@constraint:String {maxLength: 3}
public type AlphaNumeric3 string;

@constraint:String {maxLength: 4}
public type AlphaNumeric4 string;

@constraint:String {maxLength: 6}
public type AlphaNumeric6 string;

@constraint:String {maxLength: 1}
public type AlphaNumeric1 string;

public type HOSPITAL record {
    AlphaNumeric20 HOSPNAME?;
    AlphaNumeric30 HOSP\-ADDRESS?;
    AlphaNumeric10 HOSP\-PHONE?;
    AlphaNumeric20 ADMIN?;
};

public type WARD record {
    AlphaNumeric2 WARDNO?;
    AlphaNumeric3 TOT\-ROOMS?;
    AlphaNumeric3 TOT\-BEDS?;
    AlphaNumeric3 BEDAVAIL?;
    AlphaNumeric20 WARDTYPE?;
};

public type PATIENT record {
    AlphaNumeric20 PATNAME?;
    AlphaNumeric30 PATADDRESS?;
    AlphaNumeric10 PAT\-PHONE?;
    AlphaNumeric4 BEDIDENT?;
    AlphaNumeric6 DATEADMT?;
    AlphaNumeric1 PREV\-STAY\-FLAG?;
    AlphaNumeric20 PREV\-HOSP?;
    AlphaNumeric4 PREV\-DATE?;
    AlphaNumeric30 PREV\-REASON?;
};

public type SYMPTOM record {
    AlphaNumeric20 DIAGNOSE?;
    AlphaNumeric6 SYMPDATE?;
    AlphaNumeric1 PREV\-TREAT\-FLAG?;
    AlphaNumeric20 TREAT\-DESC?;
    AlphaNumeric20 SYMP\-DOCTOR?;
    AlphaNumeric10 SYMP\-DOCT\-PHONE?;
};

public type TREATMNT record {
    AlphaNumeric20 TRTYPE?;
    AlphaNumeric6 TRDATE?;
    AlphaNumeric20 MEDICATION\-TYPE?;
    AlphaNumeric30 DIET\-COMMENT?;
    AlphaNumeric1 SURGERY\-FLAG?;
    AlphaNumeric6 SURGERY\-DATE?;
    AlphaNumeric30 SURGERY\-COMMENT?;
};

public type DOCTOR record {
    AlphaNumeric20 DOCTNAME?;
    AlphaNumeric30 DOCT\-ADDRESS?;
    AlphaNumeric10 DOCT\-PHONE?;
    AlphaNumeric20 SPECIALT?;
};

public type FACILITY record {
    AlphaNumeric20 FACTYPE?;
    AlphaNumeric3 TOT\-FACIL?;
    AlphaNumeric3 FACAVAIL?;
};
