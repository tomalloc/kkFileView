


```bash
docker run --name centos -it eclipse-temurin:17.0.2_8-jdk-centos7 bash
LIBRE_OFFICE_VERSION=7.3.1
LIBRE_OFFICE_NAME=LibreOffice_${LIBRE_OFFICE_VERSION}_Linux_x86-64_rpm_sdk.tar.gz
curl -L https://download.documentfoundation.org/libreoffice/stable/${LIBRE_OFFICE_VERSION}/rpm/x86_64/${LIBRE_OFFICE_NAME} -o ${LIBRE_OFFICE_NAME}
tar -zxf $LIBRE_OFFICE_NAME



yum install -y which  libXinerama gcc perl zip

#install libreoffice
rpm -ivh *.rpm


#install sdk
rpm -ivh *.rpm

```

