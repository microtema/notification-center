CREATE INDEX IDX_Person_LdapId ON Person (ldapId);

CREATE INDEX IDX_Message_Type ON Message (messageType);
CREATE INDEX IDX_Message_Type_Person ON Message (messageType, person_id);