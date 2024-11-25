package vn.edu.likelion.front_ice.projection.challenger;

/**
 * OverviewProjection -
 *
 * @param
 * @return
 * @throws
 */
public interface OverviewProjection {
    Long getId();
    String getFirstName();
    String getLastName();
    String getAvatar();
    String getEmail();
    Boolean getIsPremium();
    Double getScore();
}
