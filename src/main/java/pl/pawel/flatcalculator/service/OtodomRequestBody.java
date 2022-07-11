package pl.pawel.flatcalculator.service;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Builder(toBuilder = true)
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class OtodomRequestBody {

    private final String query = "query CountAds($filterAttributes: FilterAttributes, $filterLocations: FilterLocations) {\n  countAds(filterAttributes: $filterAttributes, filterLocations: $filterLocations) {\n    ... on CountAds {\n      count\n      __typename\n    }\n    __typename\n  }\n}\n";
    private final String operationName = "CountAds";
    private Variable variables;

    @Builder
    @Data
    static class Variable {

        private FilterAttributes filterAttributes;
        private FilterLocations filterLocations;

        @Builder
        @Data
        @JsonInclude(JsonInclude.Include.NON_DEFAULT)
        static class FilterAttributes {

            private Market market;
            private Estate estate;
            private Transaction transaction;
            private int distanceRadius;
            private OwnerType ownerTypeSingleSelect;
            private boolean hasRemoteServices;
            private float areaMin;
            private float areaMax;
            private int priceMin;
            private int priceMax;
            private float pricePerMeterMin;
            private float pricePerMeterMax;
            private int buildYearMin;
            private int buildYearMax;
        }

        @Builder
        @Data
        static class FilterLocations {
            private List<GeoAttributesEnum> byGeoAttributes;
        }
    }

    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    @RequiredArgsConstructor
    enum GeoAttributesEnum {
        WARSZAWA("cities_6-26");

        /**
         * Some example for id values: districts_6-37, cities_6-26, streets-244923
         */
        @Getter
        private final String id;
    }

    enum Market {
        SECONDARY
    }

    enum Estate {
        FLAT
    }

    enum Transaction {
        SELL
    }

    enum OwnerType {
        ALL
    }
}
