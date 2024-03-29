CREATE TABLE IF NOT EXISTS system_message (id integer not null, content varchar(255), primary key (id));

DROP TABLE IF EXISTS application_user;
DROP TABLE IF EXISTS rating;
DROP TABLE IF EXISTS property;

CREATE TABLE IF NOT EXISTS rating (
  ratingid bigint(20) NOT NULL AUTO_INCREMENT,
  propid bigint(20) NOT NULL,
  name varchar(255) NOT NULL,
  rating integer NOT NULL,
  PRIMARY KEY (ratingid)
);

CREATE TABLE IF NOT EXISTS property (
  propid bigint(20) NOT NULL AUTO_INCREMENT,
  address varchar(100) NOT NULL,
  description varchar(100) NOT NULL,
  avgrating float(3,3) NOT NULL,
  price int(4) NOT NULL,
  propname varchar(50) NOT NULL,
  poolsize varchar(50) NOT NULL,
  PRIMARY KEY (propid),
  UNIQUE KEY unique_uk_5 (propname)
);

CREATE TABLE IF NOT EXISTS application_user (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  password varchar(255) NOT NULL,
  user_name varchar(100) NOT NULL,
  authorityr varchar(100) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY unique_uk_6 (user_name)
);


CREATE TABLE IF NOT EXISTS acl_sid (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  principal tinyint(1) NOT NULL,
  sid varchar(100) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY unique_uk_1 (sid,principal)
);

CREATE TABLE IF NOT EXISTS acl_class (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  class varchar(255) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY unique_uk_2 (class)
);

CREATE TABLE IF NOT EXISTS acl_entry (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  acl_object_identity bigint(20) NOT NULL,
  ace_order int(11) NOT NULL,
  sid bigint(20) NOT NULL,
  mask int(11) NOT NULL,
  granting tinyint(1) NOT NULL,
  audit_success tinyint(1) NOT NULL,
  audit_failure tinyint(1) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY unique_uk_4 (acl_object_identity,ace_order)
);

CREATE TABLE IF NOT EXISTS acl_object_identity (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  object_id_class bigint(20) NOT NULL,
  object_id_identity bigint(20) NOT NULL,
  parent_object bigint(20) DEFAULT NULL,
  owner_sid bigint(20) DEFAULT NULL,
  entries_inheriting tinyint(1) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY unique_uk_3 (object_id_class,object_id_identity)
);

ALTER TABLE acl_entry
ADD FOREIGN KEY (acl_object_identity) REFERENCES acl_object_identity(id);

ALTER TABLE acl_entry
ADD FOREIGN KEY (sid) REFERENCES acl_sid(id);

--
-- Constraints for table acl_object_identity
--
ALTER TABLE acl_object_identity
ADD FOREIGN KEY (parent_object) REFERENCES acl_object_identity (id);

ALTER TABLE acl_object_identity
ADD FOREIGN KEY (object_id_class) REFERENCES acl_class (id);

ALTER TABLE acl_object_identity
ADD FOREIGN KEY (owner_sid) REFERENCES acl_sid (id);
