import entity.Duty;
import util.ApplicationContext;

public class HomeServiceApp {
    public static void main(String[] args) {
//        ApplicationContext.getINSTANCE()
        Duty build = Duty.builder().id(1).build();
        System.out.println(build);
        Duty build1 = Duty.builder().parentId(1).build();
        System.out.println(build1);
        ApplicationContext.getLogger().info("start of project");
    }
}
