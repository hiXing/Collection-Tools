#chown所有者权限设置
当用户要改变一个文件的属主所使用的用户必须是该文件的属主而且同时是目标属组成员，或超级用户root。如果要连目录下的所有子目录或文件同时更改文件属主的话，直接加上-R的参数。下面来看看语法与范例，假设系统本身有test用户和test群组。
语法
usr@ubuntu:~$ chown [-R] [用户名称] [文件或目录]
usr@ubuntu:~$ chown[-R] [用户名称:组名称] [文件或目录]

范例1
语法
  将test3.txt文件的属主改为test用户。
usr@ubuntu:~$ ls -l test3.txt
-rw-r--r-- 1 test root 0 2009-10-23 9:59 test3.txt
usr@ubuntu:~$ chown test:root test3.txt
usr@ubuntu:~$ ls -l test3.txt
-rw-r--r-- 1 test root 0 2009-10-23 9:59
范例2
  chown所接的新的属主和新的属组之间可以使用:连接，属主和属组之一可以为空。如果属主为空，应该是“:属组”；如果属组为空，“:”可以不用带上。
usr@ubuntu:~$ ls -l test3.txt
-rw-r--r-- 1 test root 0 2009-10-23 9:59 test3.txt
usr@ubuntu:~$ chown :test test3.txt <==把文件test3.txt的属组改为test
usr@ubuntu:~$ ls -l test3.txt
-rw-r--r-- 1 test test 0 2009-10-23 9:59 test3.txt

范例3
  chown也提供了-R参数，这个参数对目录改变属主和属组极为有用，可以通过加-R参数来改变某个目录下的所有文件到新的属主或属组。
usr@ubuntu:~$ ls -l testdir <==查看testdir目录属性
drwxr-xr-x 2 usr root 0 2009-10-56 10:38 testdir/ <==文件属主是usr用户，属组是root用户
usr@ubuntu:~$ ls -lr testdir <==查看testdir目录下所有文件及其属性
total 0
-rw-r--r-- 1 usr root 0 2009-10-23 10:38 test1.txt
-rw-r--r-- 1 usr root 0 2009-10-23 10:38 test2.txt
-rw-r--r-- 1 usr root 0 2009-10-23 10:38 test3.txt
usr@ubuntu:~$ chown -R test:test testdir/ <==修改testdir及它的下级目录和所有文件到新的用户和用户组
usr@ubuntu:~$ ls -l testdir
drwxr-xr-x 2 test test 0 2009-10-23 10:38 testdir/
usr@ubuntu:~$ ls -lr testdir
total 0
-rw-r--r-- 1 test test 0 2009-10-23 10:38 test1.txt
-rw-r--r-- 1 test test 0 2009-10-23 10:38 test2.txt
-rw-r--r-- 1 test test 0 2009-10-23 10:38 test3.txt