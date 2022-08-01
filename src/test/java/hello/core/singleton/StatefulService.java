// 스프링 빈은 항상 무상태 (stateless) 로 설계되어야 합니다

package hello.core.singleton;
public class StatefulService {
    private int price; //상태를 유지하는 필드
    public void order(String name, int price) {
        System.out.println("name = " + name + " price = " + price);
        this.price = price; //여기가 문제!
    }
    public int getPrice() {
        return price;
    }
//    public int order(String name, int price) {
//        System.out.println("name = " + name + " price = " + price);
//        return price; //여기가 문제!
//    }
}