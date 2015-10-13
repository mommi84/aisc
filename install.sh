# sudo apt-get install libgfortran3
# alternatively:
wget http://de.archive.ubuntu.com/ubuntu/pool/main/g/gcc-4.8/libgfortran3_4.8.2-19ubuntu1_amd64.deb -O ~/libgfortran3_4.8.2-19ubuntu1_amd64.deb
dpkg-deb -x ~/libgfortran3_4.8.2-19ubuntu1_amd64.deb ~/libgfortran3
# finally:
export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:~/libgfortran3/usr/lib/x86_64-linux-gnu
java -Xmx10g -Xms1024m -jar tsoru-aisc-0.0.1-SNAPSHOT-jar-with-dependencies.jar > output.log &