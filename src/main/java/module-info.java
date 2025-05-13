module tour_planner_lamthi_kiri_puka {
    requires javafx.controls;
    requires javafx.fxml;
    requires spring.context;
    requires spring.beans;
    requires spring.data.jpa;
    requires jakarta.persistence;
    requires spring.boot.autoconfigure;
    requires spring.boot;
    requires java.sql;
    requires spring.boot.starter.data.jpa;
    requires spring.boot.starter.web;
    requires spring.boot.starter;
    requires spring.boot.starter.logging;
    requires spring.web;
    requires org.hibernate.orm.core;
    requires jakarta.annotation;
    requires spring.core;
    requires com.fasterxml.jackson.databind;
    requires org.apache.httpcomponents.httpclient;
    requires org.apache.httpcomponents.httpcore;
    requires javafx.web;
    requires org.apache.logging.log4j;
    requires org.slf4j;
    requires kernel;
    requires layout;

/*
 *  opens com.tourplannerapp to spring.core;
    opens com.tourplannerapp.controller to javafx.fxml, spring.core;
    opens com.tourplannerapp.model to org.hibernate.orm.core, spring.core, spring.beans;
*   opens com.tourplannerapp.dao to spring.core; 
*/
opens tour_planner_lamthi_kiri_puka to spring.core;
opens tour_planner_lamthi_kiri_puka.controller to javafx.fxml, spring.core;
opens tour_planner_lamthi_kiri_puka.model to org.hibernate.orm.core, spring.core, spring.beans;
opens tour_planner_lamthi_kiri_puka.dao to spring.core;

exports tour_planner_lamthi_kiri_puka;
exports tour_planner_lamthi_kiri_puka.controller;
exports tour_planner_lamthi_kiri_puka.model;
//exports tour_planner_lamthi_kiri_puka.service;
//exports tour_planner_lamthi_kiri_puka.dao;
opens tour_planner_lamthi_kiri_puka.service to org.hibernate.orm.core, spring.beans, spring.core;
//exports tour_planner_lamthi_kiri_puka.util;
opens tour_planner_lamthi_kiri_puka.util to org.hibernate.orm.core, spring.beans, spring.core;
}