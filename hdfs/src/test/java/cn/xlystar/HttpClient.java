package cn.xlystar;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.fs.permission.FsPermission;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Progressable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @ClassName: HttpClient
 * @Author: 99847
 * @Description:
 * @Date: 2022/3/22 14:59
 * @Version: 1.0
 */
public class HttpClient {
    Configuration configuration = null;
    FileSystem fs = null;

    @Before
    public void init() throws URISyntaxException, IOException, InterruptedException {
        // 1 获取文件系统
        configuration = new Configuration();
        // 配置在集群上运行
        // configuration.set("fs.defaultFS", "hdfs://linux121:9000");

        // 2 根据configuration获取Filesystem对象
        // FileSystem fs = FileSystem.get(configuration);
        FileSystem fs = FileSystem.get(new URI("hdfs://linux121:9000"), configuration, "root");

    }

    // 创建文件夹
    @Test
    public void testMkdirs() throws IOException {
        // 1 创建目录
        fs.mkdirs(new Path("/test"));
    }

    //下载文件
    @Test
    public void copyFromHdfsToLocal() throws URISyntaxException, IOException, InterruptedException {
        // boolean:是否删除源文件
        // src:hdfs路径
        // dst:目标路径，本地路径
        fs.copyToLocalFile(true, new Path("/lagou.txt"), new Path("e:/lagou_copy.txt"));
    }

    //上传文件
    @Test
    public void copyFromLocalToHdfs() throws URISyntaxException, IOException, InterruptedException {
        // 上传文件
        // src:源文件目录：本地路径
        // dst:目标文件目录，hdfs路径
        fs.copyFromLocalFile(new Path("e:/lagou.txt"), new Path("/lagou.txt"));
        //上传文件到hdfs默认是3个副本
        // 如何改变上传文件的副本数量: configuration对象中指定新的副本数量
    }

    //删除文件或者文件
    @Test
    public void deleteFile() throws URISyntaxException, IOException, InterruptedException {
        fs.delete(new Path("/api_test2"), true);
    }

    // 遍历hdfs的根目录得到文件以及文件夹的信息：名称，权限，长度等
    @Test
    public void listFiles() throws URISyntaxException, IOException, InterruptedException {
        // 得到一个迭代器：装有指定目录下所有文件信息
        RemoteIterator<LocatedFileStatus> remoteIterator = fs.listFiles(new Path("/"), true);
        // 遍历迭代器
        while (remoteIterator.hasNext()) {
            LocatedFileStatus fileStatus = remoteIterator.next();
            // 文件名称
            final String fileName = fileStatus.getPath().getName();
            // 长度
            final long len = fileStatus.getLen();
            // 权限
            final FsPermission permission = fileStatus.getPermission();
            // 分组
            final String group = fileStatus.getGroup();
            // 用户
            final String owner = fileStatus.getOwner();
            System.out.println(fileName + "\t" + len + "\t" + permission + "\t" + group + "\t" + owner);
            // 块信息
            final BlockLocation[] blockLocations = fileStatus.getBlockLocations();
            for (BlockLocation blockLocation : blockLocations) {
                final String[] hosts = blockLocation.getHosts();
                for (String host : hosts) {
                    System.out.println("主机名称" + host);
                }
            }
            System.out.println("---------------------------------");
        }
    }

    // 文件以及文件夹判断
    @Test
    public void isFile() throws URISyntaxException, IOException, InterruptedException {
        final FileStatus[] fileStatuses = fs.listStatus(new Path("/"));
        for (
                FileStatus fileStatus : fileStatuses) {
            final boolean flag = fileStatus.isFile();
            if (flag) {
                System.out.println("文件：" + fileStatus.getPath().getName());
            } else {
                System.out.println("文件夹：" + fileStatus.getPath().getName());
            }
        }

    }


    // 使用IO流操作HDFS
    // 上传文件：准备输入流读取本地文件，使用hdfs的输出流写数据到hdfs
    @Test
    public void uploadFileIO() throws IOException {
        //1. 读取本地文件的输入流
        final FileInputStream inputStream = new FileInputStream(new File("e:/lagou.txt"));
        //2. 准备写数据到hdfs的输出流
        final FSDataOutputStream outputStream = fs.create(new Path("/lagou.txt"));
        // 3.输入流数据拷贝到输出流 :数组的大小，以及是否关闭流底层有默认值
        IOUtils.copyBytes(inputStream, outputStream, configuration);
        //4.可以再次关闭流
        IOUtils.closeStream(outputStream);
        IOUtils.closeStream(inputStream);
    }

    // 上传packet
    @Test
    public void testUploadPacket() throws IOException {
        //1 准备读取本地文件的输入流
        final FileInputStream in = new FileInputStream(new File("e:/lagou.txt"));

        //2 准备好写出数据到hdfs的输出流
        final FSDataOutputStream out = fs.create(new Path("/lagou.txt"), new Progressable() {
            public void progress() {
                //这个progress方法就是每传输64KB（packet）就会执行一次
                System.out.println("&");
            }
        });

        //3 实现流拷贝
        IOUtils.copyBytes(in, out, configuration);  //默认关闭流选项是true，所以会自动 关闭

        // 4 关流 可以再次关闭也可以不关了
    }


    // 下载文件
    @Test
    public void downLoadFileIO() throws IOException {
        // 1. 读取hdfs文件的输入流
        FSDataInputStream fi = fs.open(new Path("e/xlystar.txt"));

        // 2. 本地文件的输出流
        final FileOutputStream fo = new FileOutputStream(new File("e:/lagou_io_copy.txt"));

        //3. 流的拷贝
        IOUtils.copyBytes(fi, fo, configuration);

        //4.可以再次关闭流
        IOUtils.closeStream(fo);
        IOUtils.closeStream(fi);
    }

    // seek定位读取hdfs指定文件 :使用io流读取/lagou.txt文件并把内容输出两次，本质就是读取文 件内容两次并输出
    @Test
    public void seekReadFile() throws IOException {
        // 1 创建一个读取hdfs文件的输入流
        final FSDataInputStream in = fs.open(new Path("/lagou.txt"));

        // 2.控制台数据：System.out
        // 3 实现流拷贝，输入流--》控制台输出
        // IOUtils.copyBytes(in, System.out, configuration);
        IOUtils.copyBytes(in, System.out, 4096, false);

        //4. 再次读取文件
        in.seek(0); // 定位从0偏移量（文件头部）再次读取
        IOUtils.copyBytes(in, System.out, 4096, false);

        //5.关闭输入流
        IOUtils.closeStream(in);
    }

    @After
    public void destory() throws IOException {
        fs.close();
    }

}
