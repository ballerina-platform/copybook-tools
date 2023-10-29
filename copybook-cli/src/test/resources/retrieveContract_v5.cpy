      *****************************************************************
      ** INCOM PLAN ENQUIRY COPYBOOK                                 **
      ** PROGRAM : MYDXP01C                                          **
      **                                                             **
      ** THIS COPYBOOK IS DEVELOPED AS PART OF THE WORKBENCH         **
      ** DECOMMISSIONING PROJECT                                     **
      *****************************************************************
      **  MOD #   DATE       AUTHOR  DESC                            **
      **  NEW     28MAY2011  INFOSYS CREATE                          **
WFEDPW**  WFEDPW  28AUG2012  GMDAAS  ADD NEW UNIT VALUES             **
FOFA13**  FOFA    09MAY2013  GMDAAS  NEW MATURITY RELATED FIELDS     **
FOFAN **  FOFANOV 19AUG2013  PWLRHQ  NEW FIELD MATURITY-CALCULATED-AGE*
CLMN15**  CLMN15  20JUN2015  GMDAAS  NEW FIELD FOR CLAIMS             *
      *****************************************************************
       01 DFHCOMMAREA.
          05 BROKER-MESSAGE-AREA.
             15 MI-HDR-VERSION                          PIC X(03).
             15 MI-HDR-MSGID                            PIC X(35).
             15 MI-HDR-LOGGINGID                        PIC X(35).
             15 MI-HDR-SDR-APPL                         PIC X(08).
             15 MI-HDR-SDR-USERID                       PIC X(08).
             15 MI-HDR-SDR-REGION                       PIC X(08).
             15 MI-HDR-SDR-PROCESS                      PIC X(11).
             15 MI-HDR-SDR-SVRNAME                      PIC X(11).
             15 MI-HDR-PRF-RECEIVED                     PIC X(23).
             15 MI-HDR-PRF-SENT                         PIC X(23).
             15 MI-HDR-CORRELID                         PIC X(48).
             15 MI-HDR-OPERATION                        PIC X(14).
             15 MI-HDR-REPLY                            PIC X(01).
             15 MI-HDR-REPLYSTACK                       OCCURS 02 TIMES.
                25 MI-HDR-REPLYQMGR                     PIC X(48).
                25 MI-HDR-REPLYQUEUE                    PIC X(48).
             15 FILLER                                  PIC X(92).
          05 PROCESS-REQUEST-CONTROL-AREA.
             10 PROCESS-REQUEST-DATE                    PIC  X(08).
             10 PROCESS-REQUEST-TIME                    PIC  X(06).
             10 USER-IDENTIFY                           PIC  X(08).
             10 CICS-TASK-NUMBER                        PIC S9(09).
             10 PROCESS-REQUEST-SEQUENCE-NUMB           PIC S9(04).
             10 PROCESS-REQUEST-MSG-SQNC-NUMB           PIC S9(04).
             10 PROCESS-REQUEST-RETURN-CODE             PIC  9(04).
                88 SUCCESSFUL                           VALUE 0.
                88 UNSUCCESSFUL                         VALUE 1.
             10 ERROR-DETAILS                           OCCURS 08 TIMES.
                15 ERROR-CODE                           PIC  9(04).
                15 ERROR-CONTEXT-TEXT                   OCCURS 04 TIMES
                                                        PIC  X(20).

      ****************************************************************
      *                        PLAN DETAILS                          *
      *                   LENGTH - 24801 BYTES                       *
      ****************************************************************
        02 MQ-DATA.
          03 INPUT-HDR-DATA.
             05 SRV-REQ-TYP                             PIC X(10).
             05 POL-NO                                  PIC X(10).
             05 SYS-CD                                  PIC X(03).
             05 EFF-DT                                  PIC X(08).
CLMN15       05 LIFE-INSURED-IDS                        OCCURS 5 TIMES.
CLMN15          10 LIFE-INSURED-BI-ID                   PIC 9(09).
WFEDFE       05 GROSS-SUR-VAL                           PIC -9(09).99.
WFEDFE       05 TOTAL-BONUS                             PIC -9(09).99.
WFEDFE       05 BONUS-FACTOR                            PIC 9(05).999.
          03 OUTPUT-DATA.
             05 OUTPUT-HDR-DATA.
                10 MAP-NAME                             PIC X(10).
                10 MOR-DATA-AVLBL-IND                   PIC X(01).
                10 TRANS-SEQ-NUM                        PIC 9(03).
                10 ASSOC-POL-IN-SYS                     PIC X(03).
                10 ASSOC-POL-NO                         PIC X(20).
      ****************************************************************
      *        COMMON PLAN DETAILS FOR INCOME                        *
      *                 LENGTH - 16733B                              *
      ****************************************************************
             05 COMMON-DATA.
                07 COMMON-DATA-ENQV.
                   10 TRUST-FUND-CD                     PIC X(04).
                   10 COB                               PIC X(01).
PEPE15             10 COB-DESC                          PIC X(10).
                   10 BCH-CD                            PIC X(01).
                   10 REG-CD                            PIC X(01).
                   10 POL-STAT-CD                       PIC X(02).
PEPE15             10 POL-STAT-ENQ-CD                   PIC X(02).
                   10 POL-STAT-DESC                     PIC X(30).
PEPE15             10 POL-STAT-ENQ-DESC                 PIC X(30).
                   10 TABLE-NAME                        PIC X(10).
                   10 TABLE-CODE                        PIC X(03).
                   10 PLANNER-NUM                       PIC X(08).
PEPE15             10 ORIGINATING-PLANNER-NUM           PIC X(08).
                   10 PRODUCT-NAME                      PIC X(40).
                   10 GROSS-SURR-AMT                    PIC -9(9).99.
                   10 GROSS-SURR-AMT-RED                REDEFINES
                      GROSS-SURR-AMT.
                      15 GROSS-SURR-AMT-TXT             PIC X(13).
                   10 NET-SURR-AMT                      PIC -9(9).99.
                   10 NET-SURR-AMT-RED                  REDEFINES
                      NET-SURR-AMT.
                      15 NET-SURR-AMT-TXT               PIC X(13).
                   10 MOVEMENT-CAUSE-TYPE               PIC X(02).
                   10 MOVEMENT-DT                       PIC X(08).
                   10 NEXT-PREM-BILL-DT                 PIC X(08).
                   10 PREM-RENEWED-TO-DT                PIC X(08).
                   10 PLAN-COMMENCED-DT                 PIC X(08).
                   10 PLAN-EXPIRY-DT                    PIC X(08).
                   10 PREMIUM-RATING-AGE                PIC  9(3).
                   10 PLAN-FEE-AMT                      PIC -9(9).99.
PEPE15             10 STD-FEE-IND                       PIC X(01).
                   10 PLAN-VAL-EFF-AT-DT                PIC X(08).
WFEDM              10 PLAN-VAL-RTN-EFF-DT               PIC X(08).
                   10 PREM-INDEXED-IND                  PIC X(01).
                      88 YES-IND                        VALUE 'Y'.
                      88 NO-IND                         VALUE 'N'.
                   10 PREM-PAID-TO-DT-AMT               PIC -9(9).99.
                   10 PAYMENT-METHOD                    PIC X(01).
                      88 DIRECT-DEBIT                   VALUE 'D'.
                      88 GROUP-DEDUCTION                VALUE 'G'.
                      88 PAID-UP                        VALUE 'P'.
                      88 DIRECT-BILL                    VALUE '1'.
WFEDFE             10 PAYMENT-METHOD-DESC               PIC X(15).
                   10 PAYMENT-FREQ                      PIC X(02).
                   10 SPECIAL-TREATMENT-TYPE            PIC X(01).
                   10 TEC-IND                           PIC X(01).
                      88 YES-IND                        VALUE 'Y'.
                      88 NO-IND                         VALUE 'N'.
                   10 PLAN-INSTALMENT-PREM-AMT          PIC -9(9).99.
PEPE15             10 PLAN-ANNUALISED-PREM-AMT          PIC -9(9).99.
                   10 PREM-DUE-TO-DT-AMT                PIC -9(9).99.
                   10 PURCHASED-WITH-SB-IND             PIC X(01).
                      88 YES-IND                        VALUE 'Y'.
                      88 NO-IND                         VALUE 'N'.
                   10 EX-SOURCE-CODE                    PIC X(05).
                   10 WITHDRAWAL-FEE                    PIC -9(9).99.
                   10 CAVEAT-DETAILS-COUNTER            PIC 9(2).
                   10 CAVEAT-DETAILS                    OCCURS 5 TIMES.
                      15 CAVEAT-DT                      PIC X(08).
                      15 CAVEAT-MESSAGE-TEXT            PIC X(100)   .
                   10 FIRST-POLICY-OWNER-TITLE          PIC X(08).
                   10 FIRST-POLICY-OWNER-SURNAME        PIC X(40).
                   10 FIRST-POLICY-OWNER-FIRST-NAME     PIC X(40).
                   10 FIRST-POLICY-OWNER-SECOND-NAME    PIC X(40).
                   10 FIRST-POLICY-OWNER-DOB            PIC X(08).
                   10 FIRST-POLICY-OWNER-SEX            PIC X(01).
                   10 FIRST-LIFE-INSURED-TITLE          PIC X(08).
                   10 FIRST-LIFE-INSURED-SURNAME        PIC X(40).
                   10 FIRST-LIFE-INSURED-FIRST-NAME     PIC X(40).
                   10 FIRST-LIFE-INSURED-SECOND-NAME    PIC X(40).
                   10 FIRST-LIFE-INSURED-DOB            PIC X(08).
                   10 FIRST-LIFE-INSURED-SEX            PIC X(01).
WFEDFE             10 CONTRIBUTIONS-APPLY-IND           PIC X(01).
|                  10 UNAPPLIED-CASH-EXISTS-IND         PIC X(01).
|                  10 DEATH-CLAIM-IND                   PIC X(01).
WFEDFE             10 TFN-HELD-IND                      PIC X(01).
|                  10 DISABLEMENT-CLAIM-IND             PIC X(01).
|                  10 PRODUCT-GROUP                     PIC X(06).
|                  10 REJECTED-MOVT-EXISTS-IND          PIC X(01).
WFEDFE             10 COUNTRY-CODE                      PIC X(02).
WFEDM              10 TOT-CASH-CAP-COMP-AMT             PIC -9(9).99.
FOFA13             10 TOTAL-INSURED-DEATH-AMT           PIC -9(9).99.
FOFA13             10 TOTAL-INSURED-TPD-AMT             PIC -9(9).99.
FOFA13             10 TOTAL-INSURED-IP-AMT              PIC -9(9).99.
FOFA13          07 COMMON-DATA-MATURITY.
FOFA13             10 MATURITY-VALUE-CALCULATED-IND     PIC X.
FOFA13                88 MATURITY-VALUE-CALCULATED-YES  VALUE 'Y'.
FOFA13                88 MATURITY-VALUE-CALCULATED-NO   VALUE 'N'.
FOFA13             10 MATURITY-NET-PLAN-VALUE-AMT       PIC -9(9).99.
FOFA13             10 MATURITY-GROSS-PLAN-VALUE-AMT     PIC -9(9).99.
FOFA13             10 MATURITY-VAL-ACTUAL-OR-EST-IND    PIC X.
FOFA13                88 MATURITY-VAL-ACTUAL            VALUE 'A'.
FOFA13                88 MATURITY-VAL-ESTIMATED         VALUE 'E'.
FOFA13             10 MATURITY-VAL-EFF-AT-DATE          PIC X(8).
FOFA13             10 MATURITY-CASH-VALUE-FACTOR        PIC -9(8).999.
FOFA13             10 MATURITY-EXTEND-AVAILABLE-IND     PIC X.
FOFA13                88 MATURITY-EXTEND-AVAILABLE-YES  VALUE 'Y'.
FOFA13                88 MATURITY-EXTEND-AVAILABLE-NO   VALUE 'N'.
FOFAN              10 MATURITY-CALCULATED-AGE           PIC  9(3).
CLMN15          07 COMMON-DATA-DTT-CLAIMS.
CLMN15             10 MULTI-LIFE-INS-IND                PIC  X(1).
CLMN15             10 DEATH-ANTI-DETRIMENT-AMT          PIC -9(9).99.
CLMN15             10 TOT-GROSS-DTH-CLM-PAYT-AMT        PIC -9(9).99.
CLMN15             10 TOT-NET-DTH-CLM-PAYT-AMT          PIC -9(9).99.
CLMN15             10 TOT-DTH-PAYABLE-CALC-CD           PIC X.
CLMN15                88 CALCULATED                     VALUE 'C'.
CLMN15                88 REFER                          VALUE 'R'.
CLMN15                88 NOT-APPLICABLE                 VALUE ' '.
CLMN15             10 SUM-INSURED-FIELDS-AT-EFF-DT.
CLMN15                15 TOT-DTH-BENEFITS-SUM-INS-AMT   PIC -9(9).99.
CLMN15                15 TOT-DTH-BENEFITS-SUM-CALC-CD   PIC X.
CLMN15                   88 CALCULATED                  VALUE 'C'.
CLMN15                   88 REFER                       VALUE 'R'.
CLMN15                   88 NOT-APPLICABLE              VALUE ' '.
CLMN15                15 PRT-ADD-BENEFITS-SUM-INS-AMT   PIC -9(9).99.
CLMN15                15 PRT-ADD-BENEFITS-SUM-CALC-CD   PIC X.
CLMN15                   88 CALCULATED                  VALUE 'C'.
CLMN15                   88 REFER                       VALUE 'R'.
CLMN15                   88 NOT-APPLICABLE              VALUE ' '.
CLMN15                15 TOT-TIB-BENEFITS-SUM-INS-AMT   PIC -9(9).99.
CLMN15                15 TOT-TIB-BENEFITS-CALC-CD       PIC X.
CLMN15                   88 CALCULATED                  VALUE 'C'.
CLMN15                   88 REFER                       VALUE 'R'.
CLMN15                   88 NOT-APPLICABLE              VALUE ' '.
      ****************************************************************
      *          POLICY OWNER & LIFE INSURED DATA                    *
      *             LENGTH - 6814 BYTES                              *
      ****************************************************************
                07 COMMON-DATA-CLIENT.
                   10 CLIENT-DETAIL-COUNTER             PIC 9(02).
WFEDFE             10 PLAN-CLIENT-DETAIL1               OCCURS 10 TIMES.
                      15 BUSINESS-IDENTITY-IDENTIFY     PIC 9(9).
                      15 BUSINESS-IDENTITY-TYPE         PIC X(1).
                      15 CLIENT-ROLE-CLASSIFACTION      PIC 9(04).
                      15 CLIENT-ROLE-TYPE               PIC X(05).
                      15 POLICY-OWNER-TITLE             PIC X(08).
                      15 POLICY-OWNER-SURNAME           PIC X(40).
                      15 POLICY-OWNER-FIRST-NAME        PIC X(40).
                      15 POLICY-OWNER-SECOND-NAME       PIC X(40).
                      15 POLICY-OWNER-DOB               PIC X(08).
                      15 POLICY-OWNER-SEX               PIC X(01).
                      15 LIFE-INSURED-TITLE             PIC X(08).
                      15 LIFE-INSURED-SURNAME           PIC X(40).
                      15 LIFE-INSURED-FIRST-NAME        PIC X(40).
                      15 LIFE-INSURED-SECOND-NAME       PIC X(40).
                      15 LIFE-INSURED-DOB               PIC X(08).
                      15 LIFE-INSURED-SEX               PIC X(01).
                      15 PERSON-SURNAME-INITIALS        PIC X(30).
                      15 ORGANISATION-NAME              PIC X(50).
WFEDFE                15 EMAIL-ADDRESS                  PIC X(50).
WFEDFE                15 CONTACT-TELEPHONE-NUM          PIC X(20).
WFEDFE                15 HOME-TELEPHONE-NUM             PIC X(20).
WFEDFE                15 MOBILE-NUM                     PIC X(12).
WFEDFE                15 WORK-TELEPHONE-NUM             PIC X(20).
WFEDFE                15 FAX-TELEPHONE-NUM              PIC X(20).
                   10 CLIENT-BENEFIT-COUNTER            PIC 9(02).
                   10 PLAN-CLIENT-DETAIL2               OCCURS 20 TIMES.
                      15 BENEFIT-NAME                   PIC X(05).
                      15 BENEFIT-NUM                    PIC 9(02).
                      15 BI-ID                          PIC 9(09).
                      15 BUSINESS-IDENTITY-TYPE         PIC X(01).
                      15 CLIENT-ROLE-CLASS              PIC X(02).
                      15 CLIENT-ROLE-TYPE               PIC X(05).
                      15 ORGANISATION-NAME              PIC X(50).
                      15 PERSON-GIVEN-NAME-SURNAME      PIC X(50).
                      15 PERSON-SURNAME-INITIALS        PIC X(30).
CLMN15             10 BNCY-BINDING-IND                  PIC X(01).
CLMN15             10 BNCY-BINDING-EXPIRY-DT            PIC X(08).
CLMN15             10 BNCY-CAVEATS-MORE-IND             PIC X(01).
CLMN15             10 BNCY-MORE-THAN-10-IND             PIC X(01).
CLMN15             10 BNCY-COUNTER                      PIC 9(02).
CLMN15             10 BNCY-DETAILS                      OCCURS 10 TIMES.
CLMN15                15 BNCY-GIVEN-NAMES               PIC X(25).
CLMN15                15 BNCY-SURNAME                   PIC X(25).
CLMN15                15 BNCY-RELATIONSHIP-CD           PIC X(03).
CLMN15                15 BNCY-RELATIONSHIP              PIC X(25).
CLMN15                15 BNCY-PERCENT-ALLOCATION        PIC -9(3).99.
CLMN15                15 BNCY-DOB                       PIC X(08).
CLMN15                15 BNCY-GENDER                    PIC X(01).
      ****************************************************************
      *          SUPERANNUATION BENEFIT DATA - COMMON                *
      *                   LENGTH - 0158 BYTES                        *
      ****************************************************************
                07 COMMON-DATA-ENQN.
                   10 SB-PRESENT-IND                    PIC X(01).
                      88 YES-IND                        VALUE 'Y'.
                      88 NO-IND                         VALUE 'N'.
                   10 SUPERANN-BENEFIT-DATA.
                      15 ELGBLTY-START-DT               PIC X(08).
                      15 SB-EFF-DT                      PIC X(08).
                      15 SB-FUND-COMMENCED-DT           PIC X(08).
                      15 SB-PORTION-TYPE-CD             PIC X(01).
                      15 PLAN-TOTAL-SB-AMT              PIC -9(9).99.
                      15 PLAN-TOTAL-SB-AMT-RED          REDEFINES
                         PLAN-TOTAL-SB-AMT.
                         20 PLAN-TOTAL-SB-AMT-TXT       PIC X(13).
                      15 PRESERVE-AMT                   PIC Z(9)9.99.
                      15 PRESERVE-AMT-RED               REDEFINES
                         PRESERVE-AMT.
                         20 PRESERVE-AMT-TXT            PIC X(13).
                      15 REBATE-PROPORTION-AMT          PIC 9.99.
                      15 RESTRICT-NON-PRSRV-BNFT-AMT    PIC -9(9).99.
                      15 UNRESTRT-NON-PRSRV-BNFT-AMT    PIC -9(9).99.
                      15 AT-07-ELGBLTY-START-DT         PIC X(08).
                      15 AT-07-PLAN-VAL-AMT             PIC -9(9).99.
                      15 TAX-FREE-COMPONT-AMT           PIC -9(9).99.
                      15 TAX-FREE-COMPONT-AMT-RED       REDEFINES
                         TAX-FREE-COMPONT-AMT.
                         20 TAX-FREE-COMPONT-AMT-TXT    PIC X(13).
                      15 TAX-FREE-IN-OUT-COMPONT-AMT    PIC -9(9).99.
                      15 TAX-COMPONT-AMT                PIC -9(9).99.
                      15 TAX-COMPONT-AMT-RED            REDEFINES
                         TAX-COMPONT-AMT.
                         20 TAX-COMPONT-AMT-TXT         PIC X(13).
                      15 TAX-IN-OUT-COMPONT-AMT         PIC -9(9).99.
                      15 CONDITION-OF-RELEASE           PIC X(03).
WFEDFE                15 CONDITION-OF-RELEASE-DESC      PIC X(40).
      ****************************************************************
      *            PLAN WARNING MESSAGES COMMON                      *
      *               LENGTH - 8689 BYTES                            *
      ****************************************************************
                07 COMMON-DATA-WARNING.
                   10 PLAN-WARNING-COUNTER              PIC 9(03).
CLMN15             10 PLAN-WARNING-MSGS                 OCCURS 50 TIMES.
                      15 PLAN-WARNING-TEXT              PIC X(79).
                      15 PLAN-WARNING-TYPE              PIC X(03).
                      15 PLAN-ERROR-CODE                PIC X(04).
      ****************************************************************
      *               PLAN DETAILS - CONVENTIONAL                    *
      *                  LENGTH - 8000 BYTES                         *
      ****************************************************************
CLMN15       05 PLAN-OUTPUT-DATA                        PIC X(11304).
             05 OUTPUT-DATA-CONVENTIONAL REDEFINES PLAN-OUTPUT-DATA.
                10 PLAN-DETAILS-CONVENTIONAL.
                   15 BASE-GROSS-SURR-AMT               PIC -9(9).99.
                   15 BASE-GROSS-SURR-AMT-RED           REDEFINES
                      BASE-GROSS-SURR-AMT.
                      20 BASE-GROSS-SURR-AMT-TXT        PIC X(13).
PEPE15             15 GROSS-CURR-ANNIV-VALUE-AMT        PIC -9(9).99.
PEPE15             15 GROSS-CURR-ANNIV-VALUE-AMT-RED    REDEFINES
PEPE15                GROSS-CURR-ANNIV-VALUE-AMT.
PEPE15                20 GROSS-CURR-ANNIV-VALUE-AMT-TXT PIC X(13).
PEPE15             15 GROSS-CURR-MONTH-CHG-AMT          PIC -9(9).99.
PEPE15             15 GROSS-CURR-MONTH-CHG-AMT-RED      REDEFINES
PEPE15                GROSS-CURR-MONTH-CHG-AMT.
PEPE15                20 GROSS-CURR-MONTH-CHG-AMT-TXT   PIC X(13).
                   15 PAYMENT-CAUSE-TYPE                PIC X(02).
                   15 PLAN-COMMENT-TEXT                 PIC X(50).
                   15 ASSESS-CD                         PIC X(02).
PEPE15             15 ASSESS-CD-DESC                    PIC X(20).
                   15 ASSESS-EXPIRY-DT                  PIC X(08).
                   15 ASSESS-EXTRA-ANNUAL-PREM-AMT      PIC -9(9).99.
                   15 ASSESS-IMPAIRMENT-TYPE            PIC X(02).
                   15 ASSESS-EXTRA-INSTAL-PREM-AMT      PIC -9(9).99.
                   15 ASSESS-EXTRA-SINGLE-PREM-AMT      PIC -9(9).99.
                   15 BANK-ORDER-CD                     PIC X(01).
                   15 BROKEN-TERM-IND                   PIC X(01).
                      88 YES-IND                        VALUE 'Y'.
                      88 NO-IND                         VALUE 'N'.
                   15 BUSINESS-TYPE-CD                  PIC X(01).
                   15 COST-OF-INSURANCE-AMT             PIC -9(9).99.
                   15 CPA-BALANCE-AMT                   PIC -9(9).99.
                   15 CPA-BALANCE-CAUSE-CD              PIC X(02).
                   15 CPA-BALANCE-CAUSE-DT              PIC X(08).
                   15 CPA-BALANCE-SIGN-CD               PIC X(02).
                   15 CPA-BALANCE-TOTAL-AMT             PIC -9(9).99.
                   15 CPA-BALANCE-TOT-AMT-SGN-CD        PIC X(02).
                   15 CPA-INTEREST-BILL-AMT             PIC -9(9).99.
                   15 CPA-PRINCIPLE-AMT                 PIC -9(9).99.
                   15 CURRENT-APA-BALANCE               PIC -9(9).99.
                   15 CURRENT-PREM-INTEREST-AMT         PIC -9(9).99.
                   15 FEMALE-RATE-IND                   PIC X(01).
                      88 YES-IND                        VALUE 'Y'.
                      88 NO-IND                         VALUE 'N'.
                   15 GROSS-CASH-VAL-BONUS-AMT          PIC -9(9).99.
                   15 GROUP-NUM                         PIC X(07).
                   15 INCREASE-IND                      PIC X(01).
                      88 YES-IND                        VALUE 'Y'.
                      88 NO-IND                         VALUE 'N'.
                   15 INTEREST-CAPITALISATION-DT        PIC X(08).
                   15 LIFE-INSURE-MARRIED-FEMALE-IND    PIC X(01).
                      88 YES-IND                        VALUE 'Y'.
                      88 NO-IND                         VALUE 'N'.
WFEDFE             15 LOAN-VALUE-AMT                    PIC -9(9).99.
                   15 LOAN-AVAILABLE-AMT                PIC -9(9).99.
WFEDFE             15 LOAN-DEBT-AMT                     PIC -9(9).99.
                   15 LOAN-INTEREST-BILL-AMT            PIC -9(9).99.
                   15 LOAN-PRINCIPLE-AMT                PIC -9(9).99.
                   15 LOCALITY-CD                       PIC X(03).
                   15 LOP-ACCOUNT-BALANCE-AMT           PIC -9(9).99.
                   15 LOP-ACCOUNT-BALANCE-SIGN-CD       PIC X(02).
                   15 LOP-ACCOUNT-CAUSE-CD              PIC X(02).
                   15 LOP-ACCOUNT-CAUSE-DT              PIC X(08).
                   15 MEDICAL-COMPLETE-IND              PIC X(01).
                      88 YES-IND                        VALUE 'Y'.
                      88 NO-IND                         VALUE 'N'.
                   15 NEW-BONUS-AMT                     PIC -9(9).99.
                   15 PARENT-DEAD-IND                   PIC X(01).
                      88 YES-IND                        VALUE 'Y'.
                      88 NO-IND                         VALUE 'N'.
                   15 PART-PAIDUP-IND                   PIC X(01).
                      88 YES-IND                        VALUE 'Y'.
                      88 NO-IND                         VALUE 'N'.
                   15 PLAN-ACCOUNT-BALANCE-AT-DT        PIC X(08).
                   15 PLAN-CLASS                        PIC X(04).
                   15 PLAN-DEBT-AMT                     PIC -9(9).99.
                   15 PLAN-PAIDUP-SUM-INSURED-AMT       PIC -9(9).99.
                   15 PREM-DUE-TO-DT-AMT                PIC -9(9).99.
                   15 PREM-EMERGE-DT                    PIC X(08).
                   15 PREM-INC-AMT-ALT-IND              PIC X(01).
                      88 YES-IND                        VALUE 'Y'.
                      88 NO-IND                         VALUE 'N'.
                   15 PREM-INDEX-INC-DECLINED-DT        PIC X(08).
                   15 PREM-INDEX-INC-DECLINED-IND       PIC X(01).
                      88 YES-IND                        VALUE 'Y'.
                      88 NO-IND                         VALUE 'N'.
                   15 PREM-RECOMMENCE-DT                PIC X(08).
                   15 PREM-RENEWAL-DAY                  PIC X(02).
                   15 PREM-RENEWAL-MONTH                PIC X(02).
                   15 PREM-RENEW-TO-DT                  PIC X(08).
                   15 REINSTATEMENT-TO-DT               PIC X(08).
                   15 REINSTATEMENT-ARREARS-AMT         PIC -9(9).99.
                   15 REINSURED-IND                     PIC X(01).
                      88 YES-IND                        VALUE 'Y'.
                      88 NO-IND                         VALUE 'N'.
                   15 SI-INDEX-INCR-DECLINED-DT         PIC X(08).
                   15 SI-INDEX-INCR-DECLINED-IND        PIC X(01).
                      88 YES-IND                        VALUE 'Y'.
                      88 NO-IND                         VALUE 'N'.
                   15 SINGLE-PREM-AMT                   PIC -9(9).99.
                   15 SINGLE-PREM-IND                   PIC X(01).
                      88 YES-IND                        VALUE 'Y'.
                      88 NO-IND                         VALUE 'N'.
                   15 SMOKER-ASSESS-CD                  PIC X(01).
                   15 SPECIAL-RISK-IND                  PIC X(01).
                      88 YES-IND                        VALUE 'Y'.
                      88 NO-IND                         VALUE 'N'.
                   15 SUM-INSURED-AMT                   PIC -9(9).99.
                   15 SUM-INSURED-INDEXED-IND           PIC X(01).
                      88 YES-IND                        VALUE 'Y'.
                      88 NO-IND                         VALUE 'N'.
                   15 SURR-VAL-INSUFF-DT                PIC X(08).
PEPE15             15 SURR-VAL-INSUFF-CD                PIC X(04).
                   15 SURR-VAL-MONTHLY-CHNG-AMT         PIC -9(9).99.
                   15 TEMP-ASSESS-EXPIRY-DT             PIC X(08).
                   15 TEMP-ASSESS-EXTRA-AP-AMT          PIC -9(9).99.
                   15 TEMP-ASSESS-EXTRA-IP-AMT          PIC -9(9).99.
                   15 TEMP-ASSESS-EXTRA-SGL-PREM-AMT    PIC -9(9).99.
                   15 TEMP-ASSESS-IMPAIRMENT-TYPE       PIC X(02).
                   15 TEMP-ASSESS-IND                   PIC X(01).
                      88 YES-IND                        VALUE 'Y'.
                      88 NO-IND                         VALUE 'N'.
                   15 TEMP-COMMUTE-CASH-AMT             PIC -9(9).99.
                   15 WAIVER-OF-PREM-CLAIM-DT           PIC X(08).
                   15 WAIVER-OF-PREM-CLAIM-IND          PIC X(01).
                      88 YES-IND                        VALUE 'Y'.
                      88 NO-IND                         VALUE 'N'.
                   15 CURR-AMP-GROSS-SURR-VAL-AMT       PIC -9(9).99.
                   15 CURR-AMP-SURR-VAL-MONTHLY-CHNG    PIC -9(9).99.
                   15 CURR-ANNIV-DT                     PIC X(8).
                   15 CURR-BONUS-FACTOR-RATE            PIC 9(5).999.
                   15 CURR-FIRST-THREE-YR-REV-BONUS     PIC -9(9).99.
                   15 CURR-PARTICIPATING-TYPE           PIC X(2).
                   15 CURR-CONTR-TAX-CREDIT-AMT         PIC -9(9).99.
                   15 CURR-CONVENTN-PLAN-RECORD-TYPE    PIC X(3).
                   15 CURR-ACT-GROSS-SURR-VAL-AMT       PIC -9(9).99.
                   15 CURR-ACT-SURR-VAL-MONTHLY-CHNG    PIC -9(9).99.
                   15 CURR-NETT-PREM-1-AMT              PIC -9(9).99.
                   15 CURR-NETT-PREM-2-AMT              PIC -9(9).99.
                   15 CURR-NETT-PREM-3-AMT              PIC -9(9).99.
                   15 CURR-ANNUAL-AMT                   PIC -9(9).99.
                   15 CURR-TOTAL-BONUS-AMT              PIC -9(9).99.
                   15 CURR-PREM-BEARING-SUM-INSD-AMT    PIC -9(9).99.
                   15 CURR-UTY-REVERSION-BONUS-AMT      PIC -9(9).99.
WFEDFE             15 APA-BALANCE-EXISTS-IND            PIC X(01).
WFEDFE             15 LAST-PREMIUM-PAID-DATE            PIC X(08).
WFEDFE             15 EXTERNAL-TRUSTEE-IND              PIC X(01).
                10 ACTURIAL-DETAILS-CONVENTIONAL.
                   15 OLD-AMP-GROSS-SURR-VAL-AMT        PIC -9(9).99.
                   15 OLD-AMP-SURR-VAL-MONTHLY-CHNG     PIC -9(9).99.
                   15 OLD-ANNIV-DT                      PIC X(08).
                   15 OLD-BONUS-FACTOR-RATE             PIC 9(5).999.
                   15 OLD-FIRST-THREE-YR-REV-BONUS      PIC -9(9).99.
                   15 OLD-PARTICIPATING-TYPE            PIC X(02).
                   15 OLD-CONTR-TAX-CREDIT-AMT          PIC -9(9).99.
                   15 OLD-CONVENTN-PLAN-RECORD-TYPE     PIC X(03).
                   15 OLD-ACT-GROSS-SURR-VAL-AMT        PIC -9(9).99.
                   15 OLD-ACT-SURR-VAL-MONTHLY-CHNG     PIC -9(9).99.
                   15 OLD-NETT-PREM-1-AMT               PIC -9(9).99.
                   15 OLD-NETT-PREM-2-AMT               PIC -9(9).99.
                   15 OLD-NETT-PREM-3-AMT               PIC -9(9).99.
                   15 OLD-ANNUAL-AMT                    PIC -9(9).99.
                   15 OLD-TOTAL-BONUS-AMT               PIC -9(9).99.
                   15 OLD-PREM-BEARING-SUM-INSD-AMT     PIC -9(9).99.
                   15 OLD-UTY-REVERSION-BONUS-AMT       PIC -9(9).99.
                   15 NEW-AMP-GROSS-SURR-VAL-AMT        PIC -9(9).99.
                   15 NEW-AMP-SURR-VAL-MONTHLY-CHNG     PIC -9(9).99.
                   15 NEW-ANNIV-DT                      PIC X(08).
                   15 NEW-BONUS-FACTOR-RATE             PIC 9(5).999.
                   15 NEW-FIRST-THREE-YR-REV-BONUS      PIC -9(9).99.
                   15 NEW-PARTICIPATING-TYPE            PIC X(02).
                   15 NEW-CONTR-TAX-CREDIT-AMT          PIC -9(9).99.
                   15 NEW-CONVENTN-PLAN-RECORD-TYPE     PIC X(03).
                   15 NEW-ACT-GROSS-SURR-VAL-AMT        PIC -9(9).99.
                   15 NEW-ACT-SURR-VAL-MONTHLY-CHNG     PIC -9(9).99.
                   15 NEW-NETT-PREM-1-AMT               PIC -9(9).99.
                   15 NEW-NETT-PREM-2-AMT               PIC -9(9).99.
                   15 NEW-NETT-PREM-3-AMT               PIC -9(9).99.
                   15 NEW-ANNUAL-AMT                    PIC -9(9).99.
                   15 NEW-TOTAL-BONUS-AMT               PIC -9(9).99.
                   15 NEW-PREM-BEARING-SUM-INSD-AMT     PIC -9(9).99.
                   15 NEW-UTY-REVERSION-BONUS-AMT       PIC -9(9).99.
                   15 BASIC-PREM                        PIC -9(9).99.
                   15 TOTAL-OLD-BASIC-PREM              PIC -9(9).99.
                   15 LAST-CHG-TBL-EFF-DT               PIC X(08).
                   15 LAST-TABLE-CHANGE-IND             PIC X(01).
                10 CLIENT-PLAN-ASSIGN.
                   15 PLAN-ASSIGN-DETAILS-COUNTER       PIC 9(02).
                   15 PLAN-ASSIGN-DETAILS               OCCURS 08 TIMES.
                      25 CLIENT-NAME                    PIC X(50).
                      25 ASSIGN-REGISTER-DT             PIC X(08).
                      25 ASSIGN-TRANSFER-DT             PIC X(08).
                10 REMAINING-PLAN-FIELDS.
                   15 PROPONENT-DOB                     PIC X(08).
                   15 AGE-AT-MATURITY                   PIC 9(03).
                   15 ILUST-TABLE-NAME-CODE             PIC X(08).
                   15 TRUE-TABLE-NAME                   PIC X(08).
                   15 LAPSED-IND                        PIC X(01).
                      88 YES-IND                        VALUE 'Y'.
                      88 NO-IND                         VALUE 'N'.
                   15 PAID-UP-IND                       PIC X(01).
                      88 YES-IND                        VALUE 'Y'.
                      88 NO-IND                         VALUE 'N'.
                   15 PAID-UP-DATE                      PIC X(08).
                   15 SPECIAL-CODE-1-TO-2               PIC X(02).
                   15 SPECIAL-CODE-3-TO-6               PIC X(04).
                   15 TERM-ILLNESS-BENEFIT-EXPIRY-DT    PIC X(08).
                   15 LOAN-RATE-DT                      PIC X(08).
                   15 CPA-INTEREST-RATE                 PIC -9(9).99.
                   15 LOAN-INTEREST-RATE                PIC -9(9).99.
WFEDM              15 LOAN-PERCENT                      PIC  9(3).99.
                   15 COMMERCIAL-INTEREST-RATE          PIC -9(9).99.
WFEDFE             15 CASH-VAL-BONUS-CLEAR-DEBT-AMT     PIC -9(9).99.
      ****************************************************************
      *              INCREASE DATA - CONVENTIONAL                    *
      ****************************************************************
                10 INCREASE-DATA-CONVENTIONAL.
                   15 CONVENTN-PLAN-INCR-COUNTER        PIC 9(03).
                   15 CONVENTN-PLAN-INCR                OCCURS 35 TIMES.
                      25 ANNUAL-PREM-INCR-AMT           PIC -9(9).99.
                      25 ASSESS-CD                      PIC X(02).
                      25 ASSESS-EXTR-INST-PREM-INCR-AMT PIC -9(9).99.
                      25 BENEFIT-COMMENCED-DT           PIC X(08).
                      25 BONUS-INCR-AMT                 PIC -9(9).99.
                      25 BONUS-INCR-REFER-IND           PIC X(01).
                         88 YES-IND                     VALUE 'Y'.
                         88 NO-IND                      VALUE 'N'.
                      25 CONVENTN-PLAN-INCR-NO          PIC X(03).
                      25 CPI-INCR-IND                   PIC X(01).
                         88 YES-IND                     VALUE 'Y'.
                         88 NO-IND                      VALUE 'N'.
                      25 INSTAL-PREM-INCR-AMT           PIC -9(9).99.
                      25 NETT-PREM-3-INCR-AMT           PIC -9(9).99.
                      25 NETT-PREM-3-INCR-REFER-IND     PIC X(01).
                         88 YES-IND                     VALUE 'Y'.
                         88 NO-IND                      VALUE 'N'.
                      25 NEW-BONUS-INCR-AMT             PIC -9(9).99.
                      25 NEW-BONUS-INCR-REFER-IND       PIC X(01).
                         88 YES-IND                     VALUE 'Y'.
                         88 NO-IND                      VALUE 'N'.
                      25 SUM-INSURED-INCR-AMT           PIC -9(9).99.
                      25 SUM-INSURED-INCR-REFER-IND     PIC X(01).
                         88 YES-IND                     VALUE 'Y'.
                         88 NO-IND                      VALUE 'N'.
      ****************************************************************
      *                  BENEFIT DATA - CONVENTIONAL                 *
      ****************************************************************
                10 BENEFIT-DATA-CONVENTIONAL.
                   15 BENEFIT-BASIC-DETAILS-COUNTER     PIC 9(02).
CLMN15             15 BENEFIT-BASIC-DETAILS             OCCURS 05 TIMES.
                      25 ASSESS-CD                      PIC X(02).
                      25 BENEFIT-COMMENCED-DT           PIC X(08).
                      25 BENEFIT-EXPIRY-DT              PIC X(08).
                      25 BENEFIT-NAME                   PIC X(05).
PEPE15                25 BENEFIT-NAME-DESC              PIC X(20).
                      25 BENEFIT-NUM                    PIC 9(02).
CLMN15                25 BENEFIT-STATUS-DESC            PIC X(10).
                      25 BENEFIT-SUM-INSURED-AMT        PIC -9(9).99.
CLMN15                25 BENEFIT-SUM-INS-AT-EFF-DT-AMT  PIC -9(9).99.
CLMN15                25 BENEFIT-SUM-INS-AT-EF-CALC-CD  PIC X.
CLMN15                   88 CALCULATED                  VALUE 'C'.
CLMN15                   88 REFER                       VALUE 'R'.
CLMN15                   88 NOT-APPLICABLE              VALUE ' '.
                      25 MEDICAL-COMPLETED-IND          PIC X(01).
                      25 NUM-OF-UNITS                   PIC -9(9).99.
                      25 OCCUPATION-CLASSIFICATION      PIC X(02).
                      25 ASSESS-EXTRA-ANNUAL-PREM-AMT   PIC -9(9).99.
                      25 ASSESS-EXTRA-INSTAL-PREM-AMT   PIC -9(9).99.
                      25 ASSESS-EXTRA-SINGLE-PREM-AMT   PIC -9(9).99.
                      25 BENEFIT-ANNUAL-ANNUITY-AMT     PIC -9(9).99.
                      25 BENEFIT-INSTAL-PREM-AMT        PIC -9(9).99.
                      25 BENEFIT-SINGLE-PREM-AMT        PIC -9(9).99.
                      25 COST-OF-INSURANCE-AMT          PIC -9(9).99.
                      25 REINSURED-SUM-INSURED-AMT      PIC -9(9).99.
CLMN15                25 REINSURANCE-POT-PCENT          PIC -9(3).99.
CLMN15                25 REINSURANCE-COMPANY-CD         PIC X(02).
CLMN15                25 REINSURANCE-TYPE-CD            PIC X(01).
                      25 SI-INDEX-INCR-DECLINED-DT      PIC X(08).
                      25 SI-INDEX-INCR-DECLINED-IND     PIC X(01).
                         88 YES-IND                     VALUE 'Y'.
                         88 NO-IND                      VALUE 'N'.
                      25 SMOKER-ASSESS-CD               PIC X(01).
                      25 SUM-INSURED-INDEXED-IND        PIC X(01).
                         88 YES-IND                     VALUE 'Y'.
                         88 NO-IND                      VALUE 'N'.
                      25 TEMP-ASSESS-EXPIRY-DT          PIC X(08).
                      25 TEMP-ASSESS-EXTRA-AP-AMT       PIC -9(9).99.
                      25 TEMP-ASSESS-EXTRA-IP-AMT       PIC -9(9).99.
                      25 TEMP-ASSESS-EXTRA-SGL-PREM-AMT PIC -9(9).99.
                      25 TEMP-ASSESS-IND                PIC X(01).
                         88 YES-IND                     VALUE 'Y'.
                         88 NO-IND                      VALUE 'N'.
                      25 TEMP-PROTECTION-UNIT-QUANTITY  PIC -9(9).99.
                      25 CRISIS-BENEFIT-PERCENT         PIC 9(3).
                      25 CRISIS-BENEFIT-BUYBK-ASSESS-CD PIC X(02).
                      25 TR-BENEFIT-TERM                PIC 9(02).
CLMN15                25 CONV-EXCLUSIONS-COUNTER        PIC 9(02).
CLMN15                25 CONV-EXCLUSIONS                OCCURS 5 TIMES.
CLMN15                   30 CONV-EXCL-PAID-IND          PIC X(001).
PEPE15                   30 CONV-EXCL-CLAUSE-CD         PIC X(003).
CLMN15                   30 CONV-EXCL-CLAUSE-NAME       PIC X(025).
PEPE15                   30 CONV-EXCL-ANNUAL-PREM-AMT   PIC -9(9).99.
CLMN15                   30 CONV-EXCL-EXPIRY-DT         PIC X(008).
CLMN15                   30 CONV-EXCL-ACTIVE-STATUS-IND PIC X(001).
FOFA13          10 MATURITY-VALUES-CONV.
FOFA13             15 MATURITY-SUM-INSURED-AMT          PIC -9(9).99.
FOFA13             15 MATURITY-REVERSION-BONUS-AMT      PIC -9(9).99.
FOFA13             15 MATURITY-TERMINAL-BONUS-AMT       PIC -9(9).99.
FOFA13             15 MATURITY-TAXCR-AMT                PIC -9(9).99.
FOFA13             15 MATURITY-SURCHARGE-AMT            PIC -9(9).99.
FOFA13             15 MATURITY-TOTAL-CPA-AMT            PIC -9(9).99.
FOFA13             15 MATURITY-TOTAL-LOAN-AMT           PIC -9(9).99.
CLMN15          10 CLAIM-VALUES-CONV.
CLMN15             15 DEATH-MI-BEN-LUMP-SUM-AMT         PIC -9(9).99.
CLMN15             15 DEATH-MI-BEN-LUMP-SUM-CALC-CD     PIC X.
CLMN15                88 CALCULATED                     VALUE 'C'.
CLMN15                88 REFER                          VALUE 'R'.
CLMN15                88 NOT-APPLICABLE                 VALUE ' '.
CLMN15             15 DEATH-RTN-PREM-INT-AMT            PIC -9(9).99.
CLMN15             15 DTH-RTN-PRM-INT-AMT-CALC-CD       PIC X.
CLMN15                88 RP-VALID-AMT-CALC              VALUE 'C'.
CLMN15                88 RP-UNABLE-TO-CALC-AMT          VALUE 'E'.
CLMN15                88 RP-RET-PREM-CALC-ONLY          VALUE 'R'.
CLMN15                88 RP-SURRND-VAL-CALC-ONLY        VALUE 'S'.
CLMN15                88 RP-NOT-PAYABLE-ON-DEATH        VALUE 'N'.
CLMN15             15 DEATH-REVERSION-BONUS-AMT         PIC -9(9).99.
CLMN15             15 DEATH-REVERSION-BONUS-CALC-CD     PIC X.
CLMN15                88 RB-VALID-AMT-CALC              VALUE 'C'.
CLMN15                88 RB-UNABLE-TO-CALC-AMT          VALUE 'E'.
CLMN15             15 DEATH-PROSPECTV-BONUS-AMT         PIC -9(9).99.
CLMN15             15 DEATH-PROSPECTV-BONUS-CALC-CD     PIC X.
CLMN15                88 FPB-VALID-AMT-CALC             VALUE 'C'.
CLMN15                88 FPB-UNABLE-TO-CALC-AMT         VALUE 'E'.
CLMN15                88 FPB-NOT-PAYABLE-ON-DEATH       VALUE 'N'.
CLMN15             15 DEATH-TERMINAL-BONUS-AMT          PIC -9(9).99.
CLMN15             15 DEATH-TERMINAL-BONUS-CALC-CD      PIC X.
CLMN15                88 TB-VALID-AMT-CALC              VALUE 'C'.
CLMN15                88 TB-UNABLE-TO-CALC-AMT          VALUE 'E'.
CLMN15                88 TB-NOT-PAYABLE-ON-DEATH        VALUE 'N'.
CLMN15             15 DEATH-TAXCR-AMT                   PIC -9(9).99.
CLMN15             15 DEATH-TAXCR-CALC-CD               PIC X.
CLMN15                88 TCA-VALID-AMT-CALC             VALUE 'C'.
CLMN15                88 TCA-UNABLE-TO-CALC-AMT         VALUE 'E'.
CLMN15                88 TCA-NOT-PAYABLE-ON-DEATH       VALUE 'N'.
CLMN15             15 DEATH-SURCHARGE-AMT               PIC -9(9).99.
CLMN15             15 DEATH-SURCHARGE-CALC-CD           PIC X.
CLMN15                88 TCA-VALID-AMT-CALC             VALUE 'C'.
CLMN15                88 TCA-UNABLE-TO-CALC-AMT         VALUE 'E'.
CLMN15                88 TCA-NOT-PAYABLE-ON-DEATH       VALUE 'N'.
CLMN15             15 DEATH-TOTAL-CPA-AMT               PIC -9(9).99.
CLMN15             15 DEATH-TOTAL-CPA-CALC-CD           PIC X.
CLMN15                88 CPA-NO-CPA                     VALUE 'N'.
CLMN15                88 CPA-AMT-CALC-AT-DOD            VALUE 'C'.
CLMN15                88 CPA-AMT-CALC-REQ-MAN-CHK       VALUE 'M'.
CLMN15                88 CPA-UNABLE-TO-CALC-AMT         VALUE 'E'.
CLMN15             15 DEATH-TOTAL-LOAN-AMT              PIC -9(9).99.
CLMN15             15 DEATH-TOTAL-LOAN-CALC-CD          PIC X.
CLMN15                88 LOAN-NO-LOAN                   VALUE 'N'.
CLMN15                88 LOAN-AMT-CALC-AT-DOD           VALUE 'C'.
CLMN15                88 LOAN-AMT-CALC-REQ-MAN-CHK      VALUE 'M'.
CLMN15                88 LOAN-UNABLE-TO-CALC-AMT        VALUE 'E'.
CLMN15          10 FILLER                               PIC X(2605).
      ****************************************************************
      *                  PLAN DETAILS - IL                           *
      *                  LENGTH - 8000 BYTES                         *
      ****************************************************************
             05 OUTPUT-DATA-IL  REDEFINES PLAN-OUTPUT-DATA.
                10 PLAN-DETAILS-IL.
                   15 PLAN-CLASS                        PIC X(08).
                   15 TOTAL-CASH-CAPITAL-AMT            PIC -9(9).99.
                   15 FULL-VOLUNTRY-PRSRV-PLAN-IND      PIC X(01).
                      88 YES-IND                        VALUE 'Y'.
                      88 NO-IND                         VALUE 'N'.
                   15 REDEMPTN-PERIOD-APPLY-IND         PIC X(01).
                      88 YES-IND                        VALUE 'Y'.
                      88 NO-IND                         VALUE 'N'.
                   15 PAYMENT-CAUSE-TYPE                PIC X(02).
                   15 PLAN-DETAILS-AT-DT                PIC X(08).
                   15 ANNIV-DT                          PIC X(08).
                   15 ANNUAL-INSTALMT-AMT               PIC -9(9).99.
                   15 ASSESS-CD                         PIC X(02).
PEPE15             15 ASSESS-CD-DESC                    PIC X(20).
                   15 BILL-AMT                          PIC -9(9).99.
                   15 INITIAL-UNIT-PRESENT-IND          PIC X(01).
                      88 YES-IND                        VALUE 'Y'.
                      88 NO-IND                         VALUE 'N'.
                   15 INITIAL-UNIT-VAL-AMT              PIC -9(9).99.
WFEDFE             15 INITIAL-CASH-FACTOR               PIC -9(8).999.
                   15 FULL-VAL-OF-INITIAL-UNITS         PIC -9(9).99.
                   15 FULL-VAL-OF-INVESTMENT-UNITS      PIC -9(9).99.
                   15 CASH-VAL-OF-INITIAL-UNITS         PIC -9(9).99.
                   15 CASH-VAL-OF-INVESTMENT-UNITS      PIC -9(9).99.
                   15 LAST-MOVEMENT-EFF-DT              PIC X(08).
                   15 MEDICAL-COMPLETE-IND              PIC X(01).
                      88 YES-IND                        VALUE 'Y'.
                      88 NO-IND                         VALUE 'N'.
                   15 MINIMUM-DEATH-BENEFIT-AMT         PIC -9(9).99.
                   15 OUTSTND-FEE-AMT                   PIC -9(9).99.
                   15 OUTSTND-FEE-PRESENT-IND           PIC X(01).
                      88 YES-IND                        VALUE 'Y'.
                      88 NO-IND                         VALUE 'N'.
                   15 OUTSTND-OBLIGATN-AMT              PIC -9(9).99.
                   15 PLAN-EXTEND-NAME                  PIC X(18).
                   15 PLAN-TERM                         PIC X(04).
                   15 PREM-INDEX-INC-DECLINED-IND       PIC X(01).
                      88 YES-IND                        VALUE 'Y'.
                      88 NO-IND                         VALUE 'N'.
                   15 PREM-INDEX-INC-DECLINED-DT        PIC X(08).
                   15 SINGLE-PREM-IND                   PIC X(01).
                      88 YES-IND                        VALUE 'Y'.
                      88 NO-IND                         VALUE 'N'.
                   15 SPECIAL-RISK-IND                  PIC X(01).
                      88 YES-IND                        VALUE 'Y'.
                      88 NO-IND                         VALUE 'N'.
                   15 SUM-INSURED-AMT                   PIC -9(9).99.
                   15 SUM-INSURED-INDEXED-IND           PIC X(01).
                      88 YES-IND                        VALUE 'Y'.
                      88 NO-IND                         VALUE 'N'.
                   15 SURR-VAL-INSUFF-DT                PIC X(08).
                   15 SURR-VAL-INSUFF-MSG               PIC X(13).
                   15 TOTAL-PLAN-ANNUAL-PREM-AMT        PIC -9(9).99.
                   15 TERM-ILLNESS-BENEFIT-EXPIRY-DT    PIC X(08).
WFEDFE             15 LAST-PREMIUM-PAID-DATE            PIC X(08).
WFEDFE             15 EXTERNAL-TRUSTEE-IND              PIC X(01).
                   15 PLAN-ASSIGN-DETAILS-COUNTER       PIC 9(02).
                   15 PLAN-ASSIGN-DETAILS               OCCURS 08 TIMES.
                      25 CLIENT-NAME                    PIC X(50).
                      25 ASSIGN-REGISTER-DT             PIC X(08).
                      25 ASSIGN-TRANSFER-DT             PIC X(08).
      ****************************************************************
      *                  CASH VALUE DATA - IL                        *
      ****************************************************************
                10 CASH-VALUE-DATA-IL.
                   15 PLAN-UNIT-ACCOUNT-COUNTER         PIC 9(02).
                   15 PLAN-UNIT-ACC-DATA                OCCURS 10 TIMES.
                      25 UNIT-TOTAL-VAL-AMT             PIC -9(9).99.
                      25 INVESTMT-UNIT-VAL-AMT          PIC -9(9).99.
                      25 UNIT-CLASS-IDENTIFY            PIC X(02).
                      25 INVESTMT-OPTION-PERCENT        PIC -9(9).99.
                      25 INITIAL-UNIT-QUANTITY          PIC -9(9).99.
WFEDPW                25 INITIAL-UNIT-QTY-LESS-WDL-FEE  PIC -9(9).99.
WFEDPW                25 INITIAL-UNIT-QTY-WDL-FEE       PIC -9(9).99.
                      25 INVESTMT-UNIT-QUANTITY         PIC -9(9).99.
WFEDPW                25 INVESTMT-UNIT-QTY-LESS-WDL-FEE PIC -9(9).99.
WFEDPW                25 INVESTMT-UNIT-QTY-WDL-FEE      PIC -9(9).99.
                      25 INVESTMT-OPTION-DESC           PIC X(40).
                      25 NO-OF-UNIT-QUANTITY            PIC -9(9).99.
      ****************************************************************
      *                  BENEFIT DATA - IL                           *
      ****************************************************************
                10 BENEFIT-DATA-IL.
                   15 BENEFIT-BASIC-DETAILS-COUNTER     PIC 9(02).
CLMN15             15 BENEFIT-BASIC-DETAILS             OCCURS 05 TIMES.
                      25 ASSESS-CD                      PIC X(02).
                      25 BENEFIT-COMMENCED-DT           PIC X(08).
                      25 BENEFIT-EXPIRY-DT              PIC X(08).
                      25 BENEFIT-NAME                   PIC X(05).
PEPE15                25 BENEFIT-NAME-DESC              PIC X(20).
                      25 BENEFIT-NUM                    PIC 9(02).
CLMN15                25 BENEFIT-STATUS-DESC            PIC X(10).
                      25 BENEFIT-SUM-INSURED-AMT        PIC -9(9).99.
CLMN15                25 BENEFIT-SUM-INS-AT-EFF-DT-AMT  PIC -9(9).99.
CLMN15                25 BENEFIT-SUM-INS-AT-EF-CALC-CD  PIC X.
CLMN15                   88 CALCULATED                  VALUE 'C'.
CLMN15                   88 REFER                       VALUE 'R'.
CLMN15                   88 NOT-APPLICABLE              VALUE ' '.
                      25 MEDICAL-COMPLETED-IND          PIC X(01).
                      25 OCCUPATION-CLASSIFICATION      PIC X(02).
                      25 ASSESS-EXTRA-ANNUAL-PREM-AMT   PIC -9(9).99.
                      25 ASSESS-EXTRA-INSTAL-PREM-AMT   PIC -9(9).99.
                      25 ASSESS-EXTRA-SINGLE-PREM-AMT   PIC -9(9).99.
                      25 BENEFIT-ANNUAL-ANNUITY-AMT     PIC -9(9).99.
                      25 BENEFIT-INSTAL-PREM-AMT        PIC -9(9).99.
                      25 BENEFIT-SINGLE-PREM-AMT        PIC -9(9).99.
PEPE15                25 COST-OF-INSURANCE-AMT          PIC -9(9).99.
                      25 REINSURED-SUM-INSURED-AMT      PIC -9(9).99.
CLMN15                25 REINSURANCE-POT-PCENT          PIC -9(3).99.
CLMN15                25 REINSURANCE-COMPANY-CD         PIC X(02).
CLMN15                25 REINSURANCE-TYPE-CD            PIC X(01).
                      25 SI-INDEX-INCR-DECLINED-DT      PIC X(08).
                      25 SI-INDEX-INCR-DECLINED-IND     PIC X(01).
                         88 YES-IND                     VALUE 'Y'.
                         88 NO-IND                      VALUE 'N'.
                      25 SMOKER-ASSESS-CD               PIC X(01).
                      25 SUM-INSURED-INDEXED-IND        PIC X(01).
                         88 YES-IND                     VALUE 'Y'.
                         88 NO-IND                      VALUE 'N'.
                      25 TEMP-ASSESS-EXPIRY-DT          PIC X(08).
                      25 TEMP-ASSESS-EXTRA-AP-AMT       PIC -9(9).99.
                      25 TEMP-ASSESS-EXTRA-IP-AMT       PIC -9(9).99.
                      25 TEMP-ASSESS-EXTRA-SGL-PREM-AMT PIC -9(9).99.
                      25 TEMP-ASSESS-IND                PIC X(01).
                         88 YES-IND                     VALUE 'Y'.
                         88 NO-IND                      VALUE 'N'.
                      25 TEMP-PROTECTION-UNIT-QUANTITY  PIC -9(9).99.
                      25 CRISIS-BENEFIT-PERCENT         PIC 9(3).
                      25 CRISIS-BENEFIT-BUYBK-ASSESS-CD PIC X(02).
CLMN15                25 IL-EXCLUSIONS-COUNTER          PIC 9(02).
CLMN15                25 IL-EXCLUSIONS                  OCCURS 5 TIMES.
CLMN15                   30 IL-EXCL-PAID-IND            PIC X(001).
PEPE15                   30 IL-EXCL-CLAUSE-CD           PIC X(003).
CLMN15                   30 IL-EXCL-CLAUSE-NAME         PIC X(025).
PEPE15                   30 IL-EXCL-ANNUAL-PREM-AMT     PIC -9(9).99.
CLMN15                   30 IL-EXCL-EXPIRY-DT           PIC X(008).
CLMN15                   30 IL-EXCL-ACTIVE-STATUS-IND   PIC X(001).
CLMN15          10 FILLER                               PIC X(6070).
      *  REDUCE FILLER IF YOU ADD MORE FIELDS
      ****************************************************************
      *                  PLAN DETAILS - UL                           *
      *                  LENGTH - 8000 BYTES                         *
      ****************************************************************
             05 OUTPUT-DATA-UL REDEFINES PLAN-OUTPUT-DATA.
                10 PLAN-DETAILS-UL.
                   15 EXTRA-ANNUAL-PREM-AMT             PIC -9(9).99.
PEPE15             15 UL-ANN-INV-PREM                   PIC -9(9).99.
                   15 PAYMENT-CAUSE-TYPE                PIC X(02).
                   15 PAYMENT-DATE                      PIC X(08).
                   15 PLAN-COMMENT-TEXT                 PIC X(50).
                   15 PLAN-DETAILS-AT-DT                PIC X(08).
                   15 PLAN-NAME                         PIC X(09).
                   15 PLAN-NUM                          PIC X(21).
                   15 ANNIV-DT                          PIC X(08).
                   15 ASSESS-CD                         PIC X(02).
PEPE15             15 ASSESS-CD-DESC                    PIC X(20).
                   15 BANK-ORDER-CD                     PIC X(01).
                   15 BILL-AMT                          PIC -9(9).99.
                   15 BUSINESS-TYPE-CD                  PIC X(01).
                   15 GROUP-NUM                         PIC X(07).
                   15 LAST-MOVEMENT-EFF-DT              PIC X(08).
                   15 LOCALITY-CD                       PIC X(03).
                   15 MEDICAL-COMPLETE-IND              PIC X(01).
                      88 YES-IND                        VALUE 'Y'.
                      88 NO-IND                         VALUE 'N'.
                   15 PLAN-EXTEND-NAME                  PIC X(18).
                   15 PREM-INDEX-INC-DECLINED-IND       PIC X(01).
                      88 YES-IND                        VALUE 'Y'.
                      88 NO-IND                         VALUE 'N'.
                   15 PREM-INDEX-INC-DECLINED-DT        PIC X(08).
                   15 PREM-RENEWAL-DAY                  PIC X(02).
                   15 PREM-RENEWAL-MONTH                PIC X(02).
                   15 SINGLE-PREM-PAID-TO-DT-AMT        PIC -9(9).99.
                   15 SINGLE-PREM-IND                   PIC X(01).
                      88 YES-IND                        VALUE 'Y'.
                      88 NO-IND                         VALUE 'N'.
                   15 SPECIAL-RISK-IND                  PIC X(01).
                      88 YES-IND                        VALUE 'Y'.
                      88 NO-IND                         VALUE 'N'.
                   15 TERM-ILLNESS-BENEFIT-EXPIRY-DT    PIC X(08).
                   15 PLAN-ASSIGN-DETAILS-COUNTER       PIC 9(2).
                   15 PLAN-ASSIGN-DETAILS               OCCURS 08 TIMES.
                      25 CLIENT-NAME                    PIC X(50).
                      25 ASSIGN-REGISTER-DT             PIC X(08).
                      25 ASSIGN-TRANSFER-DT             PIC X(08).
                   15 FULL-VAL-OF-INVESTMENT-UNITS      PIC -9(9).99.
                   15 CASH-VAL-OF-INVESTMENT-UNITS      PIC -9(9).99.
PEPE15             15 LAST-INVESTMENT-WEEK              PIC X(07).
      ****************************************************************
      *                  CASH VALUE DATA - UL                        *
      ****************************************************************
                10 CASH-VALUE-DATA-UL.
                   15 UNIT-TOTAL-VAL-AMT                PIC -9(9).99.
                   15 INVESTMT-UNIT-VAL-AMT             PIC -9(9).99.
                   15 UNIT-CLASS-IDENTIFY               PIC X(02).
                   15 INVESTMT-OPTION-PERCENT           PIC 9(5).99.
                   15 INITIAL-UNIT-QUANTITY             PIC -9(9).99.
                   15 INVESTMT-UNIT-QUANTITY            PIC -9(9).99.
                   15 INVESTMT-OPTION-DESC              PIC X(40).
                   15 NO-OF-UNIT-QUANTITY               PIC -9(9).99.
      ****************************************************************
      *                  BENEFIT DATA - UL                           *
      ****************************************************************
                10 BENEFIT-DATA-UL.
                   15 BENEFIT-BASIC-DETAILS-COUNTER     PIC 9(2).
CLMN15             15 BEN-DATA-UL                       OCCURS 05 TIMES.
                      25 ASSESS-CD                      PIC X(02).
                      25 BENEFIT-COMMENCED-DT           PIC X(08).
                      25 BENEFIT-EXPIRY-DT              PIC X(08).
                      25 BENEFIT-NAME                   PIC X(05).
PEPE15                25 BENEFIT-NAME-DESC              PIC X(20).
                      25 BENEFIT-NUM                    PIC 9(02).
CLMN15                25 BENEFIT-STATUS-DESC            PIC X(10).
                      25 BENEFIT-SUM-INSURED-AMT        PIC -9(9).99.
CLMN15                25 BENEFIT-SUM-INS-AT-EFF-DT-AMT  PIC -9(9).99.
CLMN15                25 BENEFIT-SUM-INS-AT-EF-CALC-CD  PIC X.
CLMN15                   88 CALCULATED                  VALUE 'C'.
CLMN15                   88 REFER                       VALUE 'R'.
CLMN15                   88 NOT-APPLICABLE              VALUE ' '.
                      25 MEDICAL-COMPLETE-IND           PIC X(01).
                      25 OCCUPATION-CLASSIFICATION      PIC X(02).
                      25 ASSESS-EXTRA-ANNUAL-PREM-AMT   PIC -9(9).99.
                      25 ASSESS-EXTRA-INSTAL-PREM-AMT   PIC -9(9).99.
                      25 ASSESS-EXTRA-SINGLE-PREM-AMT   PIC -9(9).99.
                      25 BENEFIT-ANNUAL-ANNUITY-AMT     PIC -9(9).99.
                      25 BENEFIT-INSTAL-PREM-AMT        PIC -9(9).99.
                      25 BENEFIT-SINGLE-PREM-AMT        PIC -9(9).99.
                      25 COST-OF-INSURANCE-AMT          PIC -9(9).99.
                      25 REINSURED-SUM-INSURED-AMT      PIC -9(9).99.
CLMN15                25 REINSURANCE-POT-PCENT          PIC -9(3).99.
CLMN15                25 REINSURANCE-COMPANY-CD         PIC X(02).
CLMN15                25 REINSURANCE-TYPE-CD            PIC X(01).
                      25 SI-INDEX-INCR-DECLINED-DT      PIC X(08).
                      25 SI-INDEX-INCR-DECLINED-IND     PIC X(01).
                         88 YES-IND                     VALUE 'Y'.
                         88 NO-IND                      VALUE 'N'.
                      25 SMOKER-ASSESS-CD               PIC X(01).
                      25 SUM-INSURED-INDEXED-IND        PIC X(01).
                         88 YES-IND                     VALUE 'Y'.
                         88 NO-IND                      VALUE 'N'.
                      25 TEMP-ASSESS-EXPIRY-DT          PIC X(08).
                      25 TEMP-ASSESS-EXTRA-AP-AMT       PIC -9(9).99.
                      25 TEMP-ASSESS-EXTRA-IP-AMT       PIC -9(9).99.
                      25 TEMP-ASSESS-EXTRA-SGL-PREM-AMT PIC -9(9).99.
                      25 TEMP-ASSESS-IND                PIC X(01).
                         88 YES-IND                     VALUE 'Y'.
                         88 NO-IND                      VALUE 'N'.
                      25 TEMP-PROTECTION-UNIT-QUANTITY  PIC 9(9).99.
                      25 CRISIS-BENEFIT-PERCENT         PIC 9(03).
                      25 CRISIS-BENEFIT-BUYBK-ASSESS-CD PIC X(02).
CLMN15                25 UL-EXCLUSIONS-COUNTER          PIC 9(02).
CLMN15                25 UL-EXCLUSIONS                  OCCURS 5 TIMES.
CLMN15                   30 UL-EXCL-PAID-IND            PIC X(001).
PEPE15                   30 UL-EXCL-CLAUSE-CD           PIC X(003).
CLMN15                   30 UL-EXCL-CLAUSE-NAME         PIC X(025).
PEPE15                   30 UL-EXCL-ANNUAL-PREM-AMT     PIC -9(9).99.
CLMN15                   30 UL-EXCL-EXPIRY-DT           PIC X(008).
CLMN15                   30 UL-EXCL-ACTIVE-STATUS-IND   PIC X(001).
CLMN15          10 FILLER                               PIC X(7722).
