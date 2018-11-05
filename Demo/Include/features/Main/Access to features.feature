#Author: your.email@your.domain.com
#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
#Sample Feature Definition Template
@tag
Feature: Verify the title page of feature

  @tag1
  Scenario: Verify that access to Tank feature
    Given User login PDI with valid email "automation" and password "5c57d85"
    When User selects the menu item "Maintenance>Site>Tanks"
    Then Verify the window title "TANK MAINTENANCE"
    Then Clear environment
    
  @tag2
  Scenario: Verify that access to Data Entry feature
    Given User login PDI with valid email "automation" and password "5c57d85"
    When User selects the menu item "WorkBench>Data Entry"
    Then Verify the window title "DATA ENTRY WORKBENCH OVERVIEW"
    Then Clear environment    
