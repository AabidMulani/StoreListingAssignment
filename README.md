# StoreListingAssignment
### 1) Architecture and packaging
	- Usage of RecyclerView and CardView
	- A standard packaging is implemented (even though many packages are empty)

### 2) API Calls & Parsing of response:
	- Usage of Retrofit
	- Gson for response parsing

### 3) Libraries Used:
	- Retorfit
	- OkHttp
	- Universal Image Loader
	- Timber
	- ButterKnife
	- AppCompat
	- Gson
	- Design Support Library
	- CardView | RecyclerView

### 4) Code Logic:
	- On making the rest api call we store the parsed data in an arraylist (Ganerated by Retrofit)
	- An array list of models (NearByStoreModel) is created from the above generated parser arraylist
	- Only the necessary data is ported to the ModelArrayList 
	- ModelArrayList intereacts with the adapter class and necessary information is provided to the listView (RecyclerView)


### 5) App Working:
	- Permissions for Internet and Access network state is used
	- Permission for Location is used to get your current location
	- Location listener is invoked to get your perfect location (LatLng)
	Steps Performed by the App:
		a) App checks if Gps is On if not asks to turn it on
		b) Location listener is invoked and we get your current location
		c) Call the API and populate the list
		d) Anytime cancellation while reading the location will prompt you to proceed with Pune as your current location
