package vn.edu.likelion.front_ice.mapper;

import org.mapstruct.Mapper;
import vn.edu.likelion.front_ice.dto.request.challenge.CreateChallengeRequest;
import vn.edu.likelion.front_ice.dto.response.resource.AssetsResponse;
import vn.edu.likelion.front_ice.dto.response.resource.FigmaResponse;
import vn.edu.likelion.front_ice.dto.response.resource.ResourceResponse;
import vn.edu.likelion.front_ice.entity.ResourceEntity;

/**
 * ResourceMapper -
 *
 * @param
 * @return
 * @throws
 */
@Mapper(componentModel = "spring")
public interface ResourceMapper {
    ResourceEntity toResource(CreateChallengeRequest resource);

    ResourceResponse toResourceResponse(ResourceEntity resourceEntity);

    AssetsResponse toAssetsResponse(ResourceEntity resourceEntity);

    FigmaResponse toFigmaResponse(ResourceEntity resourceEntity);
}
