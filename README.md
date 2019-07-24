# spark-cassandra
 ensure good connectivity between Apache Spark and Apache Cassandra "KeySpace creation / creation of a table / write data / read and validate the written data"


#	Application Interaction Spark/Cassandra

Cette application a pour but tester les interactions entre Apache Spark et Apache Cassandra, elle permet :
-	La création d’un KEYSPACE et la définition de sa stratégie. 
-	La création d’une table dans le KEYSPACE créer.
-	Le remplissage de la table.
-	La lecture des données écrit.
-	La vérification des données écrit. 
-	Générer un fichier résultat en format JSON, qui contient les informations du test et son résultat. 

#	Composants concernés


   Composant	   	Version
- Spark	     	2.3.2
- Cassandra		    3.11.4
- DC-OS		        1.12
- Scala		        2.11.8
- Assembly	    	0.14.9
- Gitlab		      11.3.0



# Prérequis 
Avant de lancer l’application vous devez la configurer, cela se fait au niveau du fichier de configuration de l’application, qui est dans le chemin (/src/main/resources/cassandra.conf).

# Traitements  
-	Lancer l’application sur le dcos bootstrap avec la commande 
dcos spark --name="spark-2-3" run --submit-args="--conf spark.mesos.containerizer=docker --conf spark.driver.memory=4G --conf spark.cores.max=3 --conf spark.executor.cores=1 --conf spark.executor.memory=4G --conf spark.mesos.executor.docker.forcePullImage=false --conf spark.eventLog.enabled=true --conf spark.eventLog.dir=hdfs:///spark_history  --class sparkCassandra hdfs:///jars/Spark-Cassandra -assembly-0.1.jar"
 
# Validation du test 
Vérifier le statut du test dans le fichier résultat. 
