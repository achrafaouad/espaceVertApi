//package com.example.dashborad_pipe.Repo;
//
//import com.example.dashborad_pipe.entities.EspaceVert;
//import com.example.dashborad_pipe.entities.Fuites;
//import com.example.dashborad_pipe.entities.Sites;
//import com.example.dashborad_pipe.entities.Type;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.ObjectReader;
//import org.junit.jupiter.api.Test;
//import org.locationtech.jts.geom.Coordinate;
//import org.locationtech.jts.geom.Geometry;
//import org.locationtech.jts.geom.GeometryFactory;
//import org.locationtech.jts.geom.Point;
//import org.n52.jackson.datatype.jts.JtsModule;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//@SpringBootTest
//class EspaceVertRepoTest {
//
//
//
//    @Autowired
//    private EspaceVertRepo espaceVertRepo;
//
//    public Point createPoint(double latitude, double longitude) {
//        GeometryFactory geometryFactory = new GeometryFactory();
//        Coordinate coordinate = new Coordinate(longitude, latitude);
//        return geometryFactory.createPoint(coordinate);
//    }
//
//
//
//    @Test
//    public void hello() throws JsonProcessingException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.registerModule(new JtsModule());
//        ObjectReader reader = objectMapper.readerFor(Geometry.class);
//
//        EspaceVert espaceVert = EspaceVert.builder()
//                .fuite(false)
//                .designation("sabah park")
//                .surface(1000.0)
//                .build();
//
//        // Create a list of Fuites objects
//        List<Fuites> fuites = new ArrayList<>();
//        Fuites fuite1 = Fuites.builder()
//                .date_constat(new Date())
//                .type_fuite("Type 1")
//                .type_reseau(Type.intérne)
//                .build();
//        fuites.add(fuite1);
//
//        // Set the fuites list to the espaceVert object
//        espaceVert.setFuites(fuites);
//
//        String originalGeoJson = "{ \"coordinates\": [[[ -6.883042182613622, 33.99285479160821 ], [ -6.88326496001136, 33.99280660732455 ], [ -6.883545853252457, 33.9927263001254 ], [ -6.883952664153668, 33.99261386991809 ], [ -6.884078581813839, 33.99246128582702 ], [ -6.88418512752591, 33.992276578403704 ], [ -6.884427276871747, 33.99202762428415 ], [ -6.884727542059977, 33.9918589775307 ], [ -6.884650054269514, 33.99184291591813 ], [ -6.8842432433682745, 33.9919794395321 ], [ -6.8837202007814255, 33.9923729475441 ], [ -6.883042182613622, 33.99285479160821 ]]], \"type\": \"Polygon\" }";
//        String updatedGeoJson = "{ \"coordinates\": [[[ -6.88656749462362, 33.989764256706664 ], [ -6.8868774458964594, 33.98971607205459 ], [ -6.887400488483308, 33.98916193067721 ], [ -6.8872358269288725, 33.98910571323404 ], [ -6.88848531755329, 33.987716327466174 ], [ -6.888524061448493, 33.98762798425044 ], [ -6.888524061448493, 33.9875396409429 ], [ -6.888117250437148, 33.98789301223789 ], [ -6.887255198765899, 33.98887281061809 ], [ -6.88656749462362, 33.989764256706664 ]]], \"type\": \"Polygon\" }";
//        String updatedGeoJson4 = "{ \"coordinates\": [[[ -6.886305973330593, 33.99047901303179 ], [ -6.886751528127036, 33.98989275266868 ], [ -6.886528750728388, 33.989796380715376 ], [ -6.886073509958379, 33.99035854890401 ], [ -6.886305973330593, 33.99047901303179 ]]], \"type\": \"Polygon\" }";
//        String updatedGeoJson1 = "{ \"coordinates\": [[[ -6.885598897240271, 33.99096086783511 ], [ -6.885831360612542, 33.9910572384685 ], [ -6.886218799565654, 33.99048704396736 ], [ -6.886005708141454, 33.990398703631996 ], [ -6.885802302690877, 33.99065569344371 ], [ -6.885627955161937, 33.99088858978908 ], [ -6.885598897240271, 33.99096086783511 ]]], \"type\": \"Polygon\" }";
//
//        Geometry geometry = reader.readValue(originalGeoJson);
//        Geometry geometry2 = reader.readValue(updatedGeoJson);
//        Geometry geometry3 = reader.readValue(updatedGeoJson4);
//        Geometry geometry1 = reader.readValue(updatedGeoJson1);
//
//        // Create a list of Sites objects
//        List<Sites> sitesList = new ArrayList<>();
//        Sites site1 = Sites.builder()
//                .name("Site 1")
//                .description("Description for Site 1")
//                .geom(geometry)
//                .espaceVert(espaceVert) // Set the association with espaceVert
//                .build();
//
//          Sites site2 = Sites.builder()
//                .name("Site 2")
//                .description("Description for Site 1")
//                .geom(geometry2)
//                .espaceVert(espaceVert) // Set the association with espaceVert
//                .build();
//            Sites site3 = Sites.builder()
//                .name("Site 3")
//                .description("Description for Site 1")
//                .geom(geometry3)
//                .espaceVert(espaceVert) // Set the association with espaceVert
//                .build();
//            Sites site4 = Sites.builder()
//                .name("Site 4")
//                .description("Description for Site 1")
//                .geom(geometry1)
//                .espaceVert(espaceVert) // Set the association with espaceVert
//                .build();
//
//        sitesList.add(site1);
//        sitesList.add(site2);
//        sitesList.add(site3);
//        sitesList.add(site4);
//
//        // Set the sites list to the espaceVert object
//        espaceVert.setSites(sitesList);
//
//        // Save the espaceVert object to the in-memory database
//        EspaceVert savedEspaceVert = espaceVertRepo.save(espaceVert);
//
////        System.out.println(savedEspaceVert);
//    }
//
//
//
//}