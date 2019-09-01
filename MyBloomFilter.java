package zifuchuan;

import java.util.BitSet;

public class MyBloomFilter {
    //2<<25表示32亿个比特位
    private static final int DEFAULT_SIZE= 2<< 25;
    private  static  final int[] seeds=new int[]{3,5,7,11,13,19,23,37};
    //这么大存储在BitSet
    private BitSet bits=new BitSet(DEFAULT_SIZE);
    private SimpleHash[] func=new SimpleHash[seeds.length];

    public static void main(String[] args) {
        //可疑网站
        String value="www.baidu.com";
        MyBloomFilter filter=new MyBloomFilter();
        //加入之前判断一下
        System.out.println(filter.contains(value));
        filter.add(value);
        //加入之后判断一下
        System.out.println(filter.contains(value));
    }

    //构造函数
    public MyBloomFilter(){
        for(int i=0;i<seeds.length;i++){
            func[i]=new SimpleHash(DEFAULT_SIZE,seeds[i]);
        }
    }

    //添加网站
    public void add(String value){
        for (SimpleHash f : func) {
            bits.set(f.hash(value),true);
        }
    }

    //判断可疑网站是否存在
    public boolean contains(String value){
        if(value==null){
            return false;
        }
        boolean ret=true;
        for (SimpleHash f : func) {
            ret=ret&&bits.get(f.hash(value));
        }
        return ret;
    }

    public static class SimpleHash {
        private int cap;
        private int seed;
        public SimpleHash(int cap,int seed){
            this.cap=cap;
            this.seed=seed;
        }
        public int hash(String value){
            int result=0;
            int len=value.length();
            for(int i=0;i<len;i++){
                result=seed*result+value.charAt(i);
            }
            return (cap-1)&result;
        }
    }


}
