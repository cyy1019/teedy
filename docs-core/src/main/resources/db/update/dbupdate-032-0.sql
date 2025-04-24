CREATE TABLE T_USER_REQUEST (
                                REQ_ID_C        VARCHAR(36) PRIMARY KEY,
                                REQ_USERNAME_C  VARCHAR(100),
                                REQ_EMAIL_C     VARCHAR(100),
                                REQ_REASON_C    TEXT,
                                REQ_DATE_D      TIMESTAMP,
                                REQ_STATUS_C    VARCHAR(20) -- pending / accepted / rejected
);
