# CrestronService

## 项目说明

本项目负责开启一个LocalSocket的服务端。与底层多个LocalSocket客户端进行通信。
主要职责：

* 发送大屏所有状态数据消息给客户端（对新接入客户端发送同步指令）。
* 解析客户端的指令，并设置相应功能(亮度、声音等)。
* 对客户端消息的转发给其他客户端。
* 发送大屏状态变更消息给客户端。



## 通信指令

#### From客户端指令说明

#### 格式

范例: eType:100,joinNumber:5,joinValue:0



#### 指令类型说明

eType: 100 数字数据 

joinNumber(int)对应大屏数字化控制资源,joninValue(boolean 0/1)

eType: 101 模拟数据 

joinNumber(int)对应大屏模拟化控制资源,joninValue(int)

eType: 102 串行数据        

 joinNumber(int)对应大屏串行资源，joinValue(String)





* eType: 100 数字数据 

  | 示例 | joinNumber（int） | joinValue(boolean) | 功能 |
  | ---- | ----------------- | ------------------ | ---- |
  |      |                   |                    |      |

* eType: 101 模拟数据 

  | 示例 | joinNumber(int) | joinValue(int) | 功能                |
  | ---- | --------------- | -------------- | ------------------- |
  |      | 5002            | \              | 设置亮度为joinValue |
  |      | 5012            | \              | 设置音量为joinValue |
  
* eType: 102 串行数据        

  | 示例 | joinNumber(int) | joinValue(string) | 功能 |
  | ---- | --------------- | ----------------- | ---- |
  |      |                 |                   |      |

​     

#### To客户端指令说明

#### 格式

范例: SYNC:100,5002,0



* 回复/转发/同步指令

```
格式如下：
*client send to localsocket service data format.
/*
    *eType:100,joinNumber:5002,joinValue:0 
    *eType:101,joinNumber:5012,joinValue:100
    *eType:102,joinNumber:5045,joinValue:192.168.45.34 
*/


回复/转发/同步指令格式如下：
* recv data format.
/*
    * "SYNC:100,5002,0"
    * "SYNC:101,5012,100” 
    * "SYNC:102,5045,192.168.45.34"
*/


```