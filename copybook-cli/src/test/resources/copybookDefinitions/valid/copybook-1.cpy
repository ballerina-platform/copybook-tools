01  STJ002-COMMAREA.
    02  STJ002-INPUT.
        04  STJ002-CONTROLS.
            06  STJ002-ST-CTL1     PIC  X(02).
            06  STJ002-ST-CTL2     PIC  X(03).
            06  STJ002-ST-CTL3     PIC  X(03).
            06  STJ002-ST-CTL4     PIC  X(03).
            06  STJ002-ST-ACCT     PIC  X(14).
        04  STJ002-ST-FILLER1      PIC  X(30).
    02  STJ002-OUTPUT.
        04  STJ002-CODRET          PIC  9(04).
        04  STJ002-MSJRET.
            06  STJ002-MSJRET-1    PIC  X(10).
            06  STJ002-MSJRET-2    PIC  X(40).
        04  STJ002-SITUAC          PIC  X(02).
        04  STJ002-CODPRD          PIC  X(03).
        04  STJ002-NOMPRD          PIC  X(60).
        04  STJ002-SALDIS          PIC 9(13)V99.
        04  STJ002-SALCON          PIC S9(13)V99.
        04  STJ002-CODUNI          PIC  X(12).
        04  STJ002-TIPPER          PIC  9(03).
        04  STJ002-DSCPER          PIC  X(25).
        04  STJ002-FECAPE.
            06  STJ002-SS-APE      PIC  9(02).
            06  STJ002-AA-APE      PIC  9(02).
            06  STJ002-MM-APE      PIC  9(02).
            06  STJ002-DD-APE      PIC  9(02).
        04  STJ002-RESTRICC-RETIRO PIC  X(01).
        04  STJ002-ST-FILLER2      PIC  X(52).
