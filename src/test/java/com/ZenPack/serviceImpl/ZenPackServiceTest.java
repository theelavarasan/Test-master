package com.ZenPack.serviceImpl;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;


import com.ZenPack.Dto.FeatureDto;
import com.ZenPack.Dto.MenuDto;
import com.ZenPack.Dto.ZenPackDto;
import com.ZenPack.exception.ZenPackException;
import com.ZenPack.model.FeaturedList;
import com.ZenPack.model.ZenPack;
import com.ZenPack.repository.ZenPackRepository;
import com.ZenPack.service.Impl.ZenPackServiceImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ZenPackServiceTest {
    @Mock
    private ZenPackRepository repository;

    @InjectMocks
    private ZenPackServiceImpl service;

    private ZenPackDto zenPackDto;
    private MenuDto menuDto;

    LocalDateTime myDateObj = LocalDateTime.now();
    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    String formattedDate = myDateObj.format(myFormatObj);

    @BeforeEach
    public void setUp(){
        zenPackDto= new ZenPackDto();
        zenPackDto.setZenPackId(210L);
        zenPackDto.setName("Test_Controller");
        zenPackDto.setCreatedDate(formattedDate);
        zenPackDto.setCreatedBy("Team2");
        zenPackDto.setUpdatedBy("Team2");
        zenPackDto.setUpdatedTime(formattedDate);

        menuDto = new MenuDto();
        menuDto.setFeatureUrl("https://google.com");
        menuDto.setFeatureId("f202");
        menuDto.setIsSettingMenu(true);
        menuDto.setText("test Menu");
        menuDto.setDroppable(false);
        menuDto.setParent(101);
        menuDto.setParentMenuId(101);
        menuDto.setCreatedTime(formattedDate);
        menuDto.setCreatedBy("Team2");

        FeatureDto featureDto = new FeatureDto();
        featureDto.setFeatureId("202");
        featureDto.setFeatureUrl("http://localhost:8091/api/v1/create");
        featureDto.setIcon("feature1");
        featureDto.setId(201);
        featureDto.setIsSettingMenu(true);
        featureDto.setParent(101);
        featureDto.setText("Menu Feature");

        List<FeatureDto> featureList = new ArrayList<FeatureDto>();
        featureList.add(featureDto);

        List<MenuDto> menuList = new ArrayList<MenuDto>();
        menuList.add(menuDto);
        zenPackDto.setFeatures(featureList);
        zenPackDto.setMenus(menuList);
    }
    @Test
    @DisplayName("Junit Test for Save List")
    void saveList(){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setAmbiguityIgnored(true);
        ZenPack zenPack = mapper.map(zenPackDto, ZenPack.class);

        given(repository.save(zenPack)).willReturn(zenPack);

    }

    @Test
    @DisplayName("Junit Test for getAllZenPack")
    void getAllZenPack() throws JsonProcessingException {
        ZenPackDto zenPackDto1= new ZenPackDto();
        zenPackDto1.setZenPackId(210L);
        zenPackDto1.setName("Test_Controller");
        zenPackDto1.setCreatedDate(formattedDate);
        zenPackDto1.setCreatedBy("Team2");
        zenPackDto1.setUpdatedBy("Team2");
        zenPackDto1.setUpdatedTime(formattedDate);

        menuDto = new MenuDto();
        menuDto.setFeatureUrl("https://google.com");
        menuDto.setFeatureId("f202");
        menuDto.setIsSettingMenu(true);
        menuDto.setText("test Menu");
        menuDto.setDroppable(false);
        menuDto.setParent(101);
        menuDto.setParentMenuId(101);
        menuDto.setCreatedTime(formattedDate);
        menuDto.setCreatedBy("Team2");

        FeatureDto featureDto = new FeatureDto();
        featureDto.setFeatureId("202");
        featureDto.setFeatureUrl("http://localhost:8091/api/v1/create");
        featureDto.setIcon("feature1");
        featureDto.setId(201);
        featureDto.setIsSettingMenu(true);
        featureDto.setParent(101);
        featureDto.setText("Menu Feature");

        List<FeatureDto> featureList = new ArrayList<FeatureDto>();
        featureList.add(featureDto);

        List<MenuDto> menuList = new ArrayList<MenuDto>();
        menuList.add(menuDto);
        zenPackDto.setFeatures(featureList);
        zenPackDto.setMenus(menuList);

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setAmbiguityIgnored(true);
        ZenPack zenPack = mapper.map(zenPackDto1, ZenPack.class);

        given(repository.findAll()).willReturn(Collections.singletonList(zenPack));

        List<ZenPackDto> list = service.getAllZenPack();
        assertThat(list).isNotNull();
        assertThat(list.size()).isEqualTo(1L);
    }

    @Test
    @DisplayName("Junit Test for deleteZenPack")
    void deleteZenPack(){
        Long zenPackId = 210L;
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setAmbiguityIgnored(true);
        ZenPack zenPack = mapper.map(zenPackDto, ZenPack.class);
        when(repository.findById((int) anyLong())).thenReturn(java.util.Optional.ofNullable(zenPack));
        doNothing().when(repository).delete(ArgumentMatchers.any(ZenPack.class));
        service.deleteByzenPackId(zenPackId);
//		verify(repository, times(1)).delete(zenPack);
    }



}

