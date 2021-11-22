package Database;

/**
 * @file SimpleFormSignUp.java
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SimpleFormMaps")
public class SimpleFormMaps extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SimpleFormMaps() {
		super();
	}
	
	//This method:
	//1.) Retrieves sale info from [Sale Info] Table.
	//2.) Formats sale data for html and places in ->mapDataString;
	//3.) Prints all Google Maps API code.
	//
	//	 *note: This needs to run in a browser to work.
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	  
		Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    String mapDataString = "";
	    
		try {
	         DBConnection.getDBConnection();
	         connection = DBConnection.connection;
	         
	         //I created a Sale Info Table, feel free to add columns if necessary
	         String selectSQL = "SELECT * FROM Projects";
	         preparedStatement = connection.prepareStatement(selectSQL);
	         ResultSet rs = preparedStatement.executeQuery();
	         
	         while(rs.next())
	         {
	        	 int id = rs.getInt("id");
		         String title = rs.getString("title").trim();
		         String addr1 = rs.getString("address1").trim();
		         String addr2 = rs.getString("address2").trim();
		         float lat = JsonReader.getLatitude(addr1, addr2);
		         float lon = JsonReader.getLongitude(addr1, addr2);
		         System.out.println(lat);
		         System.out.println(lon);
		         
		         //I only use MapData for the toString(). Probably didn't need to make it.
		         //The toString() gets it into the html format we need for the google maps API.
		         MapData mapData = new MapData(id, title, addr1, addr2, lat, lon);
		         mapDataString += mapData.toString() + ',';
	         }
	         mapDataString = mapDataString.substring(0, mapDataString.length() - 1);
	         System.out.println(mapDataString);
	         
	         preparedStatement.close();
	         connection.close();
	      } catch (SQLException se) {
	         se.printStackTrace();
	      } catch (Exception e) {
	         e.printStackTrace();
	      } finally {
	         try {
	            if (preparedStatement != null)
	               preparedStatement.close();
	         } catch (SQLException se2) {
	         }
	         try {
	            if (connection != null)
	               connection.close();
	         } catch (SQLException se) {
	            se.printStackTrace();
	         }
	      }
		
		//I have no idea how to get data from mySQL into a .html page, so I
		//literally just copied and pasted most of the google maps API code below.
		//
		//You can see near the bottom of this text, I add in our sale location data.
		//
		//@TODO Figure out how to get GPS coordinates of address by using Google Map Geocoding API.
		//@TODO We need to store the sale that the user clicks on, when they select 'View Sale', 
		//		so we know which sale info to display.
		PrintWriter out = response.getWriter();
		out.println("<!--\r\n" + 
	  		"  Copyright 2021 Google LLC\r\n" + 
	  		"\r\n" + 
	  		"  Licensed under the Apache License, Version 2.0 (the \"License\");\r\n" + 
	  		"  you may not use this file except in compliance with the License.\r\n" + 
	  		"  You may obtain a copy of the License at\r\n" + 
	  		"\r\n" + 
	  		"      https://www.apache.org/licenses/LICENSE-2.0\r\n" + 
	  		"\r\n" + 
	  		"  Unless required by applicable law or agreed to in writing, software\r\n" + 
	  		"  distributed under the License is distributed on an \"AS IS\" BASIS,\r\n" + 
	  		"  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\r\n" + 
	  		"  See the License for the specific language governing permissions and\r\n" + 
	  		"  limitations under the License.\r\n" + 
	  		"-->\r\n" + 
	  		"<!DOCTYPE html>\r\n" + 
	  		"<html>\r\n" + 
	  		"  <head>\r\n" + 
	  		"    <title>Locator</title>\r\n" + 
	  		"    <meta name=\"viewport\" content=\"width=device-width,initial-scale=1\">\r\n" + 
	  		"    <script src=\"https://polyfill.io/v3/polyfill.min.js?features=default\"></script>\r\n" + 
	  		"    <script src=\"https://www.gstatic.com/external_hosted/handlebars/v4.7.6/handlebars.min.js\"></script>\r\n" + 
	  		"    <link href=\"https://fonts.googleapis.com/css?family=Roboto\" rel=\"stylesheet\">\r\n" + 
	  		"    <style>\r\n" + 
	  		"      html,\r\n" + 
	  		"      body {\r\n" + 
	  		"        height: 100%;\r\n" + 
	  		"        margin: 0;\r\n" + 
	  		"        padding: 0;\r\n" + 
	  		"      }\r\n" + 
	  		"\r\n" + 
	  		"      #map-container {\r\n" + 
	  		"        width: 100%;\r\n" + 
	  		"        height: 100%;\r\n" + 
	  		"        position: relative;\r\n" + 
	  		"        font-family: \"Roboto\", sans-serif;\r\n" + 
	  		"        box-sizing: border-box;\r\n" + 
	  		"      }\r\n" + 
	  		"\r\n" + 
	  		"      #map-container button {\r\n" + 
	  		"        background: none;\r\n" + 
	  		"        color: inherit;\r\n" + 
	  		"        border: none;\r\n" + 
	  		"        padding: 0;\r\n" + 
	  		"        font: inherit;\r\n" + 
	  		"        font-size: inherit;\r\n" + 
	  		"        cursor: pointer;\r\n" + 
	  		"      }\r\n" + 
	  		"\r\n" + 
	  		"      #map {\r\n" + 
	  		"        position: absolute;\r\n" + 
	  		"        left: 22em;\r\n" + 
	  		"        top: 0;\r\n" + 
	  		"        right: 0;\r\n" + 
	  		"        bottom: 0;\r\n" + 
	  		"      }\r\n" + 
	  		"\r\n" + 
	  		"      #locations-panel {\r\n" + 
	  		"        position: absolute;\r\n" + 
	  		"        left: 0;\r\n" + 
	  		"        width: 22em;\r\n" + 
	  		"        top: 0;\r\n" + 
	  		"        bottom: 0;\r\n" + 
	  		"        overflow-y: auto;\r\n" + 
	  		"        background: white;\r\n" + 
	  		"        padding: 0.5em;\r\n" + 
	  		"        box-sizing: border-box;\r\n" + 
	  		"      }\r\n" + 
	  		"\r\n" + 
	  		"      @media only screen and (max-width: 876px) {\r\n" + 
	  		"        #map {\r\n" + 
	  		"          left: 0;\r\n" + 
	  		"          bottom: 50%;\r\n" + 
	  		"        }\r\n" + 
	  		"\r\n" + 
	  		"        #locations-panel {\r\n" + 
	  		"          top: 50%;\r\n" + 
	  		"          right: 0;\r\n" + 
	  		"          width: unset;\r\n" + 
	  		"        }\r\n" + 
	  		"      }\r\n" + 
	  		"\r\n" + 
	  		"      #locations-panel-list > header {\r\n" + 
	  		"        padding: 1.4em 1.4em 0 1.4em;\r\n" + 
	  		"      }\r\n" + 
	  		"\r\n" + 
	  		"      #locations-panel-list h1.search-title {\r\n" + 
	  		"        font-size: 1em;\r\n" + 
	  		"        font-weight: 500;\r\n" + 
	  		"        margin: 0;\r\n" + 
	  		"      }\r\n" + 
	  		"\r\n" + 
	  		"      #locations-panel-list h1.search-title > img {\r\n" + 
	  		"        vertical-align: bottom;\r\n" + 
	  		"        margin-top: 1em;\r\n" + 
	  		"      }\r\n" + 
	  		"\r\n" + 
	  		"      #locations-panel-list .search-input {\r\n" + 
	  		"        width: 100%;\r\n" + 
	  		"        margin-top: 0.8em;\r\n" + 
	  		"        position: relative;\r\n" + 
	  		"      }\r\n" + 
	  		"\r\n" + 
	  		"      #locations-panel-list .search-input input {\r\n" + 
	  		"        width: 100%;\r\n" + 
	  		"        border: 1px solid rgba(0, 0, 0, 0.2);\r\n" + 
	  		"        border-radius: 0.3em;\r\n" + 
	  		"        height: 2.2em;\r\n" + 
	  		"        box-sizing: border-box;\r\n" + 
	  		"        padding: 0 2.5em 0 1em;\r\n" + 
	  		"        font-size: 1em;\r\n" + 
	  		"      }\r\n" + 
	  		"\r\n" + 
	  		"      #locations-panel-list .search-input-overlay {\r\n" + 
	  		"        position: absolute;\r\n" + 
	  		"      }\r\n" + 
	  		"\r\n" + 
	  		"      #locations-panel-list .search-input-overlay.search {\r\n" + 
	  		"        right: 2px;\r\n" + 
	  		"        top: 2px;\r\n" + 
	  		"        bottom: 2px;\r\n" + 
	  		"        width: 2.4em;\r\n" + 
	  		"      }\r\n" + 
	  		"\r\n" + 
	  		"      #locations-panel-list .search-input-overlay.search button {\r\n" + 
	  		"        width: 100%;\r\n" + 
	  		"        height: 100%;\r\n" + 
	  		"        border-radius: 0.2em;\r\n" + 
	  		"        color: black;\r\n" + 
	  		"        background: transparent;\r\n" + 
	  		"      }\r\n" + 
	  		"\r\n" + 
	  		"      #locations-panel-list .search-input-overlay.search .icon {\r\n" + 
	  		"        margin-top: 0.05em;\r\n" + 
	  		"        vertical-align: top;\r\n" + 
	  		"      }\r\n" + 
	  		"\r\n" + 
	  		"      #locations-panel-list .section-name {\r\n" + 
	  		"        font-weight: 500;\r\n" + 
	  		"        font-size: 0.9em;\r\n" + 
	  		"        margin: 1.8em 0 1em 1.5em;\r\n" + 
	  		"      }\r\n" + 
	  		"\r\n" + 
	  		"      #locations-panel-list .location-result {\r\n" + 
	  		"        position: relative;\r\n" + 
	  		"        padding: 0.8em 3.5em 0.8em 1.4em;\r\n" + 
	  		"        border-bottom: 1px solid rgba(0, 0, 0, 0.12);\r\n" + 
	  		"        cursor: pointer;\r\n" + 
	  		"      }\r\n" + 
	  		"\r\n" + 
	  		"      #locations-panel-list .location-result:first-of-type {\r\n" + 
	  		"        border-top: 1px solid rgba(0, 0, 0, 0.12);\r\n" + 
	  		"      }\r\n" + 
	  		"\r\n" + 
	  		"      #locations-panel-list .location-result:last-of-type {\r\n" + 
	  		"        border-bottom: none;\r\n" + 
	  		"      }\r\n" + 
	  		"\r\n" + 
	  		"      #locations-panel-list .location-result.selected {\r\n" + 
	  		"        outline: 2px solid #4285f4;\r\n" + 
	  		"      }\r\n" + 
	  		"\r\n" + 
	  		"      #locations-panel-list button.select-location {\r\n" + 
	  		"        margin-bottom: 0.6em;\r\n" + 
	  		"        text-align: left;\r\n" + 
	  		"      }\r\n" + 
	  		"\r\n" + 
	  		"      #locations-panel-list .location-result h2.name {\r\n" + 
	  		"        font-size: 1em;\r\n" + 
	  		"        font-weight: 500;\r\n" + 
	  		"        margin: 0;\r\n" + 
	  		"      }\r\n" + 
	  		"\r\n" + 
	  		"      #locations-panel-list .location-result .address {\r\n" + 
	  		"        font-size: 0.9em;\r\n" + 
	  		"        margin-bottom: 0.5em;\r\n" + 
	  		"      }\r\n" + 
	  		"\r\n" + 
	  		"      #locations-panel-list .location-result .distance {\r\n" + 
	  		"        position: absolute;\r\n" + 
	  		"        top: 0.9em;\r\n" + 
	  		"        right: 0;\r\n" + 
	  		"        text-align: center;\r\n" + 
	  		"        font-size: 0.9em;\r\n" + 
	  		"        width: 5em;\r\n" + 
	  		"      }\r\n" + 
	  		"\r\n" + 
	  		"      #location-results-list {\r\n" + 
	  		"        list-style-type: none;\r\n" + 
	  		"        margin: 0;\r\n" + 
	  		"        padding: 0;\r\n" + 
	  		"      }\r\n" + 
	  		"    </style>\r\n" + 
	  		"    <script>\r\n" + 
	  		"      'use strict';\r\n" + 
	  		"\r\n" + 
	  		"      /**\r\n" + 
	  		"       * Defines an instance of the Locator+ solution, to be instantiated\r\n" + 
	  		"       * when the Maps library is loaded.\r\n" + 
	  		"       */\r\n" + 
	  		"      function LocatorPlus(configuration) {\r\n" + 
	  		"        const locator = this;\r\n" + 
	  		"\r\n" + 
	  		"        locator.locations = configuration.locations || [];\r\n" + 
	  		"        locator.capabilities = configuration.capabilities || {};\r\n" + 
	  		"\r\n" + 
	  		"        const mapEl = document.getElementById('map');\r\n" + 
	  		"        const panelEl = document.getElementById('locations-panel');\r\n" + 
	  		"        locator.panelListEl = document.getElementById('locations-panel-list');\r\n" + 
	  		"        const sectionNameEl =\r\n" + 
	  		"            document.getElementById('location-results-section-name');\r\n" + 
	  		"        const resultsContainerEl = document.getElementById('location-results-list');\r\n" + 
	  		"\r\n" + 
	  		"        const itemsTemplate = Handlebars.compile(\r\n" + 
	  		"            document.getElementById('locator-result-items-tmpl').innerHTML);\r\n" + 
	  		"\r\n" + 
	  		"        locator.searchLocation = null;\r\n" + 
	  		"        locator.searchLocationMarker = null;\r\n" + 
	  		"        locator.selectedLocationIdx = null;\r\n" + 
	  		"        locator.userCountry = null;\r\n" + 
	  		"\r\n" + 
	  		"        // Initialize the map -------------------------------------------------------\r\n" + 
	  		"        locator.map = new google.maps.Map(mapEl, configuration.mapOptions);\r\n" + 
	  		"\r\n" + 
	  		"        // Store selection.\r\n" + 
	  		"        const selectResultItem = function(locationIdx, panToMarker, scrollToResult) {\r\n" + 
	  		"          locator.selectedLocationIdx = locationIdx;\r\n" + 
	  		"          for (let locationElem of resultsContainerEl.children) {\r\n" + 
	  		"            locationElem.classList.remove('selected');\r\n" + 
	  		"            if (getResultIndex(locationElem) === locator.selectedLocationIdx) {\r\n" + 
	  		"              locationElem.classList.add('selected');\r\n" + 
	  		"              if (scrollToResult) {\r\n" + 
	  		"                panelEl.scrollTop = locationElem.offsetTop;\r\n" + 
	  		"              }\r\n" + 
	  		"            }\r\n" + 
	  		"          }\r\n" + 
	  		"          if (panToMarker && (locationIdx != null)) {\r\n" + 
	  		"            locator.map.panTo(locator.locations[locationIdx].coords);\r\n" + 
	  		"          }\r\n" + 
	  		"        };\r\n" + 
	  		"\r\n" + 
	  		"        // Create a marker for each location.\r\n" + 
	  		"        const markers = locator.locations.map(function(location, index) {\r\n" + 
	  		"          const marker = new google.maps.Marker({\r\n" + 
	  		"            position: location.coords,\r\n" + 
	  		"            map: locator.map,\r\n" + 
	  		"            title: location.title,\r\n" + 
	  		"          });\r\n" + 
	  		"          marker.addListener('click', function() {\r\n" + 
	  		"            selectResultItem(index, false, true);\r\n" + 
	  		"          });\r\n" + 
	  		"          return marker;\r\n" + 
	  		"        });\r\n" + 
	  		"\r\n" + 
	  		"        // Fit map to marker bounds.\r\n" + 
	  		"        locator.updateBounds = function() {\r\n" + 
	  		"          const bounds = new google.maps.LatLngBounds();\r\n" + 
	  		"          if (locator.searchLocationMarker) {\r\n" + 
	  		"            bounds.extend(locator.searchLocationMarker.getPosition());\r\n" + 
	  		"          }\r\n" + 
	  		"          for (let i = 0; i < markers.length; i++) {\r\n" + 
	  		"            bounds.extend(markers[i].getPosition());\r\n" + 
	  		"          }\r\n" + 
	  		"          locator.map.fitBounds(bounds);\r\n" + 
	  		"        };\r\n" + 
	  		"        if (locator.locations.length) {\r\n" + 
	  		"          locator.updateBounds();\r\n" + 
	  		"        }\r\n" + 
	  		"\r\n" + 
	  		"        // Get the distance of a store location to the user's location,\r\n" + 
	  		"        // used in sorting the list.\r\n" + 
	  		"        const getLocationDistance = function(location) {\r\n" + 
	  		"          if (!locator.searchLocation) return null;\r\n" + 
	  		"\r\n" + 
	  		"          // Use travel distance if available (from Distance Matrix).\r\n" + 
	  		"          if (location.travelDistanceValue != null) {\r\n" + 
	  		"            return location.travelDistanceValue;\r\n" + 
	  		"          }\r\n" + 
	  		"\r\n" + 
	  		"          // Fall back to straight-line distance.\r\n" + 
	  		"          return google.maps.geometry.spherical.computeDistanceBetween(\r\n" + 
	  		"              new google.maps.LatLng(location.coords),\r\n" + 
	  		"              locator.searchLocation.location);\r\n" + 
	  		"        };\r\n" + 
	  		"\r\n" + 
	  		"        // Render the results list --------------------------------------------------\r\n" + 
	  		"        const getResultIndex = function(elem) {\r\n" + 
	  		"          return parseInt(elem.getAttribute('data-location-index'));\r\n" + 
	  		"        };\r\n" + 
	  		"\r\n" + 
	  		"        locator.renderResultsList = function() {\r\n" + 
	  		"          let locations = locator.locations.slice();\r\n" + 
	  		"          for (let i = 0; i < locations.length; i++) {\r\n" + 
	  		"            locations[i].index = i;\r\n" + 
	  		"          }\r\n" + 
	  		"          if (locator.searchLocation) {\r\n" + 
	  		"            sectionNameEl.textContent =\r\n" + 
	  		"                'Nearest locations (' + locations.length + ')';\r\n" + 
	  		"            locations.sort(function(a, b) {\r\n" + 
	  		"              return getLocationDistance(a) - getLocationDistance(b);\r\n" + 
	  		"            });\r\n" + 
	  		"          } else {\r\n" + 
	  		"            sectionNameEl.textContent = `All locations (${locations.length})`;\r\n" + 
	  		"          }\r\n" + 
	  		"          const resultItemContext = { locations: locations };\r\n" + 
	  		"          resultsContainerEl.innerHTML = itemsTemplate(resultItemContext);\r\n" + 
	  		"          for (let item of resultsContainerEl.children) {\r\n" + 
	  		"            const resultIndex = getResultIndex(item);\r\n" + 
	  		"            if (resultIndex === locator.selectedLocationIdx) {\r\n" + 
	  		"              item.classList.add('selected');\r\n" + 
	  		"            }\r\n" + 
	  		"\r\n" + 
	  		"            const resultSelectionHandler = function() {\r\n" + 
	  		"              selectResultItem(resultIndex, true, false);\r\n" + 
	  		"            };\r\n" + 
	  		"\r\n" + 
	  		"            // Clicking anywhere on the item selects this location.\r\n" + 
	  		"            // Additionally, create a button element to make this behavior\r\n" + 
	  		"            // accessible under tab navigation.\r\n" + 
	  		"            item.addEventListener('click', resultSelectionHandler);\r\n" + 
	  		"            item.querySelector('.select-location')\r\n" + 
	  		"                .addEventListener('click', function(e) {\r\n" + 
	  		"                  resultSelectionHandler();\r\n" + 
	  		"                  e.stopPropagation();\r\n" + 
	  		"                });\r\n" + 
	  		"          }\r\n" + 
	  		"        };\r\n" + 
	  		"\r\n" + 
	  		"        // Optional capability initialization --------------------------------------\r\n" + 
	  		"        initializeSearchInput(locator);\r\n" + 
	  		"        initializeDistanceMatrix(locator);\r\n" + 
	  		"\r\n" + 
	  		"        // Initial render of results -----------------------------------------------\r\n" + 
	  		"        locator.renderResultsList();\r\n" + 
	  		"      }\r\n" + 
	  		"\r\n" + 
	  		"      /** When the search input capability is enabled, initialize it. */\r\n" + 
	  		"      function initializeSearchInput(locator) {\r\n" + 
	  		"        const geocodeCache = new Map();\r\n" + 
	  		"        const geocoder = new google.maps.Geocoder();\r\n" + 
	  		"\r\n" + 
	  		"        const searchInputEl = document.getElementById('location-search-input');\r\n" + 
	  		"        const searchButtonEl = document.getElementById('location-search-button');\r\n" + 
	  		"\r\n" + 
	  		"        const updateSearchLocation = function(address, location) {\r\n" + 
	  		"          if (locator.searchLocationMarker) {\r\n" + 
	  		"            locator.searchLocationMarker.setMap(null);\r\n" + 
	  		"          }\r\n" + 
	  		"          if (!location) {\r\n" + 
	  		"            locator.searchLocation = null;\r\n" + 
	  		"            return;\r\n" + 
	  		"          }\r\n" + 
	  		"          locator.searchLocation = {'address': address, 'location': location};\r\n" + 
	  		"          locator.searchLocationMarker = new google.maps.Marker({\r\n" + 
	  		"            position: location,\r\n" + 
	  		"            map: locator.map,\r\n" + 
	  		"            title: 'My location',\r\n" + 
	  		"            icon: {\r\n" + 
	  		"              path: google.maps.SymbolPath.CIRCLE,\r\n" + 
	  		"              scale: 12,\r\n" + 
	  		"              fillColor: '#3367D6',\r\n" + 
	  		"              fillOpacity: 0.5,\r\n" + 
	  		"              strokeOpacity: 0,\r\n" + 
	  		"            }\r\n" + 
	  		"          });\r\n" + 
	  		"\r\n" + 
	  		"          // Update the locator's idea of the user's country, used for units. Use\r\n" + 
	  		"          // `formatted_address` instead of the more structured `address_components`\r\n" + 
	  		"          // to avoid an additional billed call.\r\n" + 
	  		"          const addressParts = address.split(' ');\r\n" + 
	  		"          locator.userCountry = addressParts[addressParts.length - 1];\r\n" + 
	  		"\r\n" + 
	  		"          // Update map bounds to include the new location marker.\r\n" + 
	  		"          locator.updateBounds();\r\n" + 
	  		"\r\n" + 
	  		"          // Update the result list so we can sort it by proximity.\r\n" + 
	  		"          locator.renderResultsList();\r\n" + 
	  		"\r\n" + 
	  		"          locator.updateTravelTimes();\r\n" + 
	  		"        };\r\n" + 
	  		"\r\n" + 
	  		"        const geocodeSearch = function(query) {\r\n" + 
	  		"          if (!query) {\r\n" + 
	  		"            return;\r\n" + 
	  		"          }\r\n" + 
	  		"\r\n" + 
	  		"          const handleResult = function(geocodeResult) {\r\n" + 
	  		"            searchInputEl.value = geocodeResult.formatted_address;\r\n" + 
	  		"            updateSearchLocation(\r\n" + 
	  		"                geocodeResult.formatted_address, geocodeResult.geometry.location);\r\n" + 
	  		"          };\r\n" + 
	  		"\r\n" + 
	  		"          if (geocodeCache.has(query)) {\r\n" + 
	  		"            handleResult(geocodeCache.get(query));\r\n" + 
	  		"            return;\r\n" + 
	  		"          }\r\n" + 
	  		"          const request = {address: query, bounds: locator.map.getBounds()};\r\n" + 
	  		"          geocoder.geocode(request, function(results, status) {\r\n" + 
	  		"            if (status === 'OK') {\r\n" + 
	  		"              if (results.length > 0) {\r\n" + 
	  		"                const result = results[0];\r\n" + 
	  		"                geocodeCache.set(query, result);\r\n" + 
	  		"                handleResult(result);\r\n" + 
	  		"              }\r\n" + 
	  		"            }\r\n" + 
	  		"          });\r\n" + 
	  		"        };\r\n" + 
	  		"\r\n" + 
	  		"        // Set up geocoding on the search input.\r\n" + 
	  		"        searchButtonEl.addEventListener('click', function() {\r\n" + 
	  		"          geocodeSearch(searchInputEl.value.trim());\r\n" + 
	  		"        });\r\n" + 
	  		"\r\n" + 
	  		"        // Initialize Autocomplete.\r\n" + 
	  		"        initializeSearchInputAutocomplete(\r\n" + 
	  		"            locator, searchInputEl, geocodeSearch, updateSearchLocation);\r\n" + 
	  		"      }\r\n" + 
	  		"\r\n" + 
	  		"      /** Add Autocomplete to the search input. */\r\n" + 
	  		"      function initializeSearchInputAutocomplete(\r\n" + 
	  		"          locator, searchInputEl, fallbackSearch, searchLocationUpdater) {\r\n" + 
	  		"        // Set up Autocomplete on the search input. Bias results to map viewport.\r\n" + 
	  		"        const autocomplete = new google.maps.places.Autocomplete(searchInputEl, {\r\n" + 
	  		"          types: ['geocode'],\r\n" + 
	  		"          fields: ['place_id', 'formatted_address', 'geometry.location']\r\n" + 
	  		"        });\r\n" + 
	  		"        autocomplete.bindTo('bounds', locator.map);\r\n" + 
	  		"        autocomplete.addListener('place_changed', function() {\r\n" + 
	  		"          const placeResult = autocomplete.getPlace();\r\n" + 
	  		"          if (!placeResult.geometry) {\r\n" + 
	  		"            // Hitting 'Enter' without selecting a suggestion will result in a\r\n" + 
	  		"            // placeResult with only the text input value as the 'name' field.\r\n" + 
	  		"            fallbackSearch(placeResult.name);\r\n" + 
	  		"            return;\r\n" + 
	  		"          }\r\n" + 
	  		"          searchLocationUpdater(\r\n" + 
	  		"              placeResult.formatted_address, placeResult.geometry.location);\r\n" + 
	  		"        });\r\n" + 
	  		"      }\r\n" + 
	  		"\r\n" + 
	  		"      /** Initialize Distance Matrix for the locator. */\r\n" + 
	  		"      function initializeDistanceMatrix(locator) {\r\n" + 
	  		"        const distanceMatrixService = new google.maps.DistanceMatrixService();\r\n" + 
	  		"\r\n" + 
	  		"        // Annotate travel times to the selected location using Distance Matrix.\r\n" + 
	  		"        locator.updateTravelTimes = function() {\r\n" + 
	  		"          if (!locator.searchLocation) return;\r\n" + 
	  		"\r\n" + 
	  		"          const units = (locator.userCountry === 'USA') ?\r\n" + 
	  		"              google.maps.UnitSystem.IMPERIAL :\r\n" + 
	  		"              google.maps.UnitSystem.METRIC;\r\n" + 
	  		"          const request = {\r\n" + 
	  		"            origins: [locator.searchLocation.location],\r\n" + 
	  		"            destinations: locator.locations.map(function(x) {\r\n" + 
	  		"              return x.coords;\r\n" + 
	  		"            }),\r\n" + 
	  		"            travelMode: google.maps.TravelMode.DRIVING,\r\n" + 
	  		"            unitSystem: units,\r\n" + 
	  		"          };\r\n" + 
	  		"          const callback = function(response, status) {\r\n" + 
	  		"            if (status === 'OK') {\r\n" + 
	  		"              const distances = response.rows[0].elements;\r\n" + 
	  		"              for (let i = 0; i < distances.length; i++) {\r\n" + 
	  		"                const distResult = distances[i];\r\n" + 
	  		"                let travelDistanceText, travelDistanceValue;\r\n" + 
	  		"                if (distResult.status === 'OK') {\r\n" + 
	  		"                  travelDistanceText = distResult.distance.text;\r\n" + 
	  		"                  travelDistanceValue = distResult.distance.value;\r\n" + 
	  		"                }\r\n" + 
	  		"                const location = locator.locations[i];\r\n" + 
	  		"                location.travelDistanceText = travelDistanceText;\r\n" + 
	  		"                location.travelDistanceValue = travelDistanceValue;\r\n" + 
	  		"              }\r\n" + 
	  		"\r\n" + 
	  		"              // Re-render the results list, in case the ordering has changed.\r\n" + 
	  		"              locator.renderResultsList();\r\n" + 
	  		"            }\r\n" + 
	  		"          };\r\n" + 
	  		"          distanceMatrixService.getDistanceMatrix(request, callback);\r\n" + 
	  		"        };\r\n" + 
	  		"      }\r\n" + 
	  		"    </script>\r\n" + 
	  		"    <script>\r\n" + 
	  		"      \r\n" + 
	  		"      const CONFIGURATION = {\r\n" + 
	  		"	        \"locations\": [\r\n" + 
	  		//MYSQL DATA INSERTED HERE
	  		mapDataString + 
	  		
	  		"	          ],\r\n" + 
	  		"	          \"mapOptions\": {\"center\":{\"lat\":38.0,\"lng\":-100.0},\"fullscreenControl\":true,\"mapTypeControl\":false,\"streetViewControl\":false,\"zoom\":4,\"zoomControl\":true,\"maxZoom\":17},\r\n" + 
	  		"	          \"mapsApiKey\": \"AIzaSyBrxQ6cDHtbLklLr5z25xJxQAOHB0LqRe4\"\r\n" + 
	  		"	        };\r\n" + 
	  		"\r\n" + 
	  		"      function initMap() {\r\n" + 
	  		"        new LocatorPlus(CONFIGURATION);\r\n" + 
	  		"      }\r\n" + 
	  		"    </script>\r\n" + 
	  		"    <script id=\"locator-result-items-tmpl\" type=\"text/x-handlebars-template\">\r\n" + 
	  		"      {{#each locations}}\r\n" + 
	  		"        <li class=\"location-result\" data-location-index=\"{{index}}\">\r\n" + 
	  		"          <button class=\"select-location\">\r\n" + 
	  		"            <h2 class=\"name\">{{title}}</h2>\r\n" + 
	  		"          </button>\r\n" + 
	  		"          <div class=\"address\">{{address1}}<br>{{address2}}</div>\r\n" + 
	  		"			<form action=\"SimpleFormSale\" method=\"POST\">\r\n" + 
	  		"    			<input type=\"submit\" name={{index}} value=\"View Sale\" />\r\n" +
	  		"		    </form>\r\n" + 
	  		"          {{#if travelDistanceText}}\r\n" + 
	  		"            <div class=\"distance\">{{travelDistanceText}}</div>\r\n" + 
	  		"          {{/if}}\r\n" + 
	  		"        </li>\r\n" + 
	  		"			\r\n" + 
	  		"      {{/each}}\r\n" + 
	  		"    </script>\r\n" + 
	  		"  </head>\r\n" + 
	  		"  <body>\r\n" + 
	  		"    <div id=\"map-container\">\r\n" + 
	  		"      <div id=\"locations-panel\">\r\n" + 
	  		"        <div id=\"locations-panel-list\">\r\n" + 
	  		"          <header>\r\n" + 
	  		"			<form action=\"/OmaFund/youIn.html\">\r\n" + 
	  		"    			<input type=\"submit\" value=\"Back\" />\r\n" + 
	  		"			</form>" +
	  		"            <h1 class=\"search-title\">\r\n" + 
	  		"              <img src=\"https://fonts.gstatic.com/s/i/googlematerialicons/place/v15/24px.svg\"/>\r\n" + 
	  		"              Find a Sale Near You\r\n" + 
	  		"            </h1>\r\n" + 
	  		"            <div class=\"search-input\">\r\n" + 
	  		"              <input id=\"location-search-input\" placeholder=\"Enter your address or zip code\">\r\n" + 
	  		"              <div id=\"search-overlay-search\" class=\"search-input-overlay search\">\r\n" + 
	  		"                <button id=\"location-search-button\">\r\n" + 
	  		"                  <img class=\"icon\" src=\"https://fonts.gstatic.com/s/i/googlematerialicons/search/v11/24px.svg\" alt=\"Search\"/>\r\n" + 
	  		"                </button>\r\n" + 
	  		"              </div>\r\n" + 
	  		"            </div>\r\n" + 
	  		"          </header>\r\n" + 
	  		"          <div class=\"section-name\" id=\"location-results-section-name\">\r\n" + 
	  		"            All locations\r\n" + 
	  		"          </div>\r\n" + 
	  		"          <div class=\"results\">\r\n" + 
	  		"            <ul id=\"location-results-list\"></ul>\r\n" + 
	  		"          </div>\r\n" + 
	  		"        </div>\r\n" + 
	  		"      </div>\r\n" + 
	  		"      <div id=\"map\"></div>\r\n" + 
	  		"    </div>\r\n" + 
	  		"    <script src=\"https://maps.googleapis.com/maps/api/js?key=AIzaSyBrxQ6cDHtbLklLr5z25xJxQAOHB0LqRe4&callback=initMap&libraries=places,geometry&solution_channel=GMP_QB_locatorplus_v4_cABD\" async defer></script>\r\n" + 
	  		"  </body>\r\n" + 
	  		"</html>");
      
   }

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
