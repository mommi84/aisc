mkdir aisc
cd aisc
# sudo apt-get install libgfortran3
# alternatively:
wget http://de.archive.ubuntu.com/ubuntu/pool/main/g/gcc-4.8/libgfortran3_4.8.2-19ubuntu1_amd64.deb
dpkg-deb -x libgfortran3_4.8.2-19ubuntu1_amd64.deb libgfortran3
export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:$(pwd)/libgfortran3/usr/lib/x86_64-linux-gnu
# finally:
wget https://s3.eu-central-1.amazonaws.com/tommaso-soru/jar/tsoru-aisc-0.0.2-SNAPSHOT-jar-with-dependencies.jar -O aisc.jar
wget https://s3.eu-central-1.amazonaws.com/tommaso-soru/jar/stopwords-en.txt
mkdir word2vec
wget https://s3.amazonaws.com/dl4j-distribution/GoogleNews-vectors-negative300.bin.gz -O word2vec/GoogleNews-vectors-negative300.bin.gz
mkdir datasets
wget https://s3.eu-central-1.amazonaws.com/tommaso-soru/jar/training_set.tsv -O datasets/training_set.tsv
wget https://s3.eu-central-1.amazonaws.com/tommaso-soru/jar/validation_set.tsv -O datasets/validation_set.tsv
java -Xmx32g -Xms1024m -jar aisc.jar > output.log &