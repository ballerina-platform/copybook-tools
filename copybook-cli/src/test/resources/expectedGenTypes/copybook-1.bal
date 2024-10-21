import ballerina/constraint;

@constraint:String {maxLength: 2}
public type AlphaNumeric2 string;

@constraint:String {maxLength: 3}
public type AlphaNumeric3 string;

@constraint:String {maxLength: 14}
public type AlphaNumeric14 string;

@constraint:String {maxLength: 30}
public type AlphaNumeric30 string;

@constraint:Int {minValue: 0, maxDigits: 4}
public type UnsignedInteger4 int;

@constraint:String {maxLength: 10}
public type AlphaNumeric10 string;

@constraint:String {maxLength: 40}
public type AlphaNumeric40 string;

@constraint:String {maxLength: 60}
public type AlphaNumeric60 string;

@constraint:Number {minValue: 0, maxIntegerDigits: 13, maxFractionDigits: 2}
public type UnsignedDecimal13V2 decimal;

@constraint:Number {maxIntegerDigits: 13, maxFractionDigits: 2}
public type SignedDecimal13V2 decimal;

@constraint:String {maxLength: 12}
public type AlphaNumeric12 string;

@constraint:Int {minValue: 0, maxDigits: 3}
public type UnsignedInteger3 int;

@constraint:String {maxLength: 25}
public type AlphaNumeric25 string;

@constraint:Int {minValue: 0, maxDigits: 2}
public type UnsignedInteger2 int;

@constraint:String {maxLength: 1}
public type AlphaNumeric1 string;

@constraint:String {maxLength: 52}
public type AlphaNumeric52 string;

public type STJ002\-COMMAREA record {
    record {record {AlphaNumeric2 STJ002\-ST\-CTL1?; AlphaNumeric3 STJ002\-ST\-CTL2?; AlphaNumeric3 STJ002\-ST\-CTL3?; AlphaNumeric3 STJ002\-ST\-CTL4?; AlphaNumeric14 STJ002\-ST\-ACCT?;} STJ002\-CONTROLS?; AlphaNumeric30 STJ002\-ST\-FILLER1?;} STJ002\-INPUT?;
    record {UnsignedInteger4 STJ002\-CODRET?; record {AlphaNumeric10 STJ002\-MSJRET\-1?; AlphaNumeric40 STJ002\-MSJRET\-2?;} STJ002\-MSJRET?; AlphaNumeric2 STJ002\-SITUAC?; AlphaNumeric3 STJ002\-CODPRD?; AlphaNumeric60 STJ002\-NOMPRD?; UnsignedDecimal13V2 STJ002\-SALDIS?; SignedDecimal13V2 STJ002\-SALCON?; AlphaNumeric12 STJ002\-CODUNI?; UnsignedInteger3 STJ002\-TIPPER?; AlphaNumeric25 STJ002\-DSCPER?; record {UnsignedInteger2 STJ002\-SS\-APE?; UnsignedInteger2 STJ002\-AA\-APE?; UnsignedInteger2 STJ002\-MM\-APE?; UnsignedInteger2 STJ002\-DD\-APE?;} STJ002\-FECAPE?; AlphaNumeric1 STJ002\-RESTRICC\-RETIRO?; AlphaNumeric52 STJ002\-ST\-FILLER2?;} STJ002\-OUTPUT?;
};
