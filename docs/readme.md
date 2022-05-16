
# 环境变量说明

## 自动配置

1. setsdkenv_unix  -> for Unix-like systems
2. setsdkenv_windows.bat  -> for Windows


| **OFFICE_HOME**     | **Path to an existing LibreOffice installation, e.g. "/opt/libreoffice8".** |
|---------------------|------------------------------------------------------------------------------|
| OO_SDK_NAME         | The configuration uses the directory name of the SDK, but you can use another name as well. It is important that the name does not contain spaces, because of a limitation of gnu make which cannot handle spaces correctly. This name is, for example, used to create an output directory in the users home directory for any generated output when you use the SDK build environment. |
| OO_SDK_HOME         | Path to the SDK root directory, e.g. "/opt/libreoffice/sdk"   |
| OO_SDK_JAVA_HOME    | Path to a JDK root directory. The JDK is optional and only necessary if you like to run the Java examples or if you like to use the SDK environment to develop your own Java solution.  |
| OO_SDK_CPP_HOME     | Path to the C++ compiler (on Windows, the directory where the "vcvar32.bat" file can be found). The C++ compiler is optional and only necessary for building the C++ examples.   |
| OO_SDK_CLI_HOME     | Path to the C# compiler and VB.NET compiler (on Windows, this directory can be found under the system directory (e.g. c:\WINXP\Microsoft.NET\Framework\v1.0.3705). The compilers are optional and only necessary for building the CLI examples.Note: Windows only!             |
| OO_SDK_MAKE_HOME    | Path to GNU make.                                                 |
| OO_SDK_ZIP_HOME     | Path to the 'zip' tool.                                            |
| OO_SDK_CAT_HOME     | Path to the 'cat' tool.                                           |
| OO_SDK_SED_HOME     | Path to the 'sed' tool.                                           |
| OO_SDK_OUTPUT_DIR   | Path to an existing directory where the example output is generated. The output directory is optional, by default the output is generated in the SDK directory itself. If an output directory is specified, the output is generated in an SDK dependent subdirectory in this directory (e.g. &lt;OO_SDK_OUTPUT_DIR&gt; /LibreOffice 7.3/LINUXExample.out)                               |
| SDK_AUTO_DEPLOYMENT | If this variable is set, the component examples are automatically deployed into the LibreOffice installation referenced by OFFICE_HOME. See also chapter "Extension Manager - unopkg" from the Developer's Guide.                                                                |


## Manual Setting


| **OO_SDK_NAME**     | **See description above.**                                         |
|---------------------|-----------------------------------------------------------------------|
| OO_SDK_URE_BIN_DIR  | The path within the chosen LibreOffice URE installation where binary executables are located.                                                               |
| OO_SDK_URE_LIB_DIR  | The path within the chosen LibreOffice URE installation where dynamic libraries are located.                                                       |
| OO_SDK_URE_JAVA_DIR | The path within the chosen LibreOffice URE installation where Java JARs are located.                                                                    |
| CLASSPATH           | `=$OO_SDK_URE_JAVA_DIR/libreoffice.jar; $OO_SDK_URE_JAVA_DIR/unoloader.jar; $CLASSPATH` The classpath will be set or extended to the necessary jar files of the specified LibreOffice installation.                       |
| OFFICE_PROGRAM_PATH | `=$OFFICE_HOME/program` This variable is used to find, for example, the office type library and the UNO package deployment tool.  |
| UNO_PATH            | `=$OFFICE_PROGRAM_PATH` This variable is used to ensure that the new C++ UNO bootstrap mechanism uses the configured LibreOffice installation of the SDK. Normally the bootstrap mechanism finds the default office installation for the user on the system. This variable is optional but is set from the scripts to ensure a homogeneous environment. Especially useful during development where you might have more than one office installation installed. |





see: https://api.libreoffice.org/docs/install.html