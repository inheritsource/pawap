Default database name is always motricedb
Default user is always motriceuser

Run scripts as postgres user. Either log in as
postgres, or execute with sudo as below. 

Create a database:
sudo  su postgres ./create_database.sh
sudo  su postgres ./create_database.sh <dbname> <user>

Delete a database:
sudo  su postgres ./delete_database.sh
sudo  su postgres ./delete_database.sh <dbname>

Create a user:
sudo  su postgres ./create_user.sh
sudo  su postgres ./create_user.sh <user>

Delete a user:
sudo  su postgres ./delete_user.sh
sudo  su postgres ./delete_user.sh <user>

Create motrice tables:
sudo  su postgres ./create_tables.sh

#### OBSOLETE, this is done with Coordinatrice ########
# Insert base demo data to motricedb:
# sudo sh -c "./execute_file.sh motricedb ../create/motrice.postgres.demodata.sql"

# Backup of the data only:  Note: For the moment table mtf_tag_type is excluded
sudo  su postgres ./backup_database.sh
sudo  su postgres ./backup_database.sh <dbname>

Restore of data only:
sudo sh -c ./execute_file.sh <dbname> <backup file> ./motricedb.backup
sudo sh -c ./execute_file.sh motricedb ./motricedb.backup





