import ballerina/constraint;

@constraint:String {maxLength: 100}
public type AlphaNumeric100 string;

@constraint:Int {minValue: 0, maxDigits: 4}
public type UnsignedInteger4 int;

@constraint:String {maxLength: 96}
public type AlphaNumeric96 string;

@constraint:String {maxLength: 50}
public type AlphaNumeric50 string;

@constraint:String {maxLength: 46}
public type AlphaNumeric46 string;

public type TEST record {
    AlphaNumeric100 Field\-1?;
    record {UnsignedInteger4 Field\-2?; AlphaNumeric96 Field\-3?;} Group\-1?;
    record {UnsignedInteger4 Field\-4?; AlphaNumeric50 Field\-5?; AlphaNumeric46 Field\-6?;} Group\-2?;
};
