package application.projection;



public interface DutyResponseChildrenProjection {
    Integer getId;
    private String title;

    private String parentTitle;

    private Boolean selectable;

    private Set<DutyResponseChildren> subDuty = new HashSet<>();
}
