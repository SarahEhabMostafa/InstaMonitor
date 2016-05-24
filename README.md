#InstaMonitor
A library that monitors the time spent in an application and its activities and fragments


##Usage
For each activity that you want to monitor, extend InstaMonitorActivity
For each fragment that you want to monitor, extend InstaMonitorFragment

If you wish to ignore the time spent on a certain activity, just call isIgnored(true)

The class `InstaMonitorDatabaseInterface` contains all the get, update, insert and delete methods that you might need
