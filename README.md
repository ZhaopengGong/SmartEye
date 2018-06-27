#### SmartEye

#### 效果预览

| ![](https://github.com/xygzp415/SmartEye/blob/master/screenshot/menu.png) | ![](https://github.com/xygzp415/SmartEye/blob/master/screenshot/crop.png) | ![](https://github.com/xygzp415/SmartEye/blob/master/screenshot/result.png) |
| :----------------------------------: | :----------------------------------: | :----------------------------------: |
|               菜单界面               |               裁剪图片               |               识别结果               |

#### 功能

识别食物。用户对食物进行拍照或者是从相册选择食物照片，裁剪照片后调用将裁剪后图片上传到服务器，服务器返回识别的结果已经营养成分如纤维素，蛋白质含量等

#### 编译环境

- AndoidStudio 3.0.0
- gradle版本3.0.0
- compileSdkVersion 26

#### 使用说明

- 先搭建好服务端，服务端源码地址
- 在utils包下将url_prefix字段替换为自己的服务端ip

#### 参考

识别加载动画使用了 [**MISportsConnectWidget**](https://github.com/sickworm/MISportsConnectWidget/tree/master/misportsconnectview)