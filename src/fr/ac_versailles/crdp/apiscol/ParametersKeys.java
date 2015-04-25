package fr.ac_versailles.crdp.apiscol;

public enum ParametersKeys {
	commonPropertiesFilePath("common_properties_file_path"), specificPropertiesFilePath(
			"specific_properties_file_path"), fileRepoPath("file_repo_path"), previewsRepoPath(
			"previews_repo_path"), temporaryFilesPrefix(
			"temporary_files_prefix"), apiscolInstanceName(
			"apiscol_instance_name"), apiscolInstanceLabel(
			"apiscol_instance_label"), solrAddress("solr_address"), solrSearchPath(
			"solr_search_path"), solrUpdatePath("solr_update_path"), solrExtractPath(
			"solr_extract_path"), solrSuggestPath("solr_suggest_path"), sharedSecret(
			"shared_secret"), contentWsSharedSecret("content_ws_shared_secret"), metaWsSharedSecret(
			"meta_ws_shared_secret"), packWsSharedSecret(
			"pack_ws_shared_secret"), thumbsWsSharedSecret(
			"thumbs_ws_shared_secret"), conversionWsSharedSecret(
			"conversion_ws_shared_secret"), contentWebserviceUrl(
			"content_webservice_url"), metadataWebserviceUrl(
			"metadata_webservice_url"), packWebserviceUrl("pack_webservice_url"), thumbsWebserviceUrl(
			"thumbs_webservice_url"), conversionWebserviceUrl(
			"conversion_webservice_url"), systemDefaultLanguage(
			"system_default_language"), user("user"), password("password"), absolutePageLimit(
			"absolute_page_limit"), apiscolVersion("apiscol_version"), cleaningDelay(
			"cleaning_delay"), automaticThumbChoiceDelay(
			"automatic_thumb_choice_delay"), automaticThumbChoiceMaxNumberOfTries(
			"automatic_thumb_choice_max_number_of_tries"), logLevel("log_level"), epubPreviewQuality("epub_preview_quality");
	private String value;

	private ParametersKeys(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}

}
