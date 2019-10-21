package com.springBoot.elasticSearch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springBoot.response.Response;
import com.springBoot.user.model.Note;
import com.springBoot.utility.ResponseHelper;
import com.springBoot.utility.TokenGeneration;

@Service
public class ElasticSearchImpl implements ElasticSearchOfNote {

	private final String INDEX = "fundoonotes";
	private final String TYPE = "notes";

	@Autowired
	private RestHighLevelClient restHighLevelClient;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private TokenGeneration tokenUtil;

	public Response createNote(Note note) {
		@SuppressWarnings("rawtypes")
		Map dataMap = objectMapper.convertValue(note, Map.class);
		IndexRequest indexRequest = new IndexRequest(INDEX, TYPE, String.valueOf(note.getNoteid())).source(dataMap);
		try {
			restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
		} catch (ElasticsearchException e) {
			e.getDetailedMessage();
		} catch (java.io.IOException ex) {
			ex.getLocalizedMessage();
		}
		return ResponseHelper.statusResponse(200, "Note created");
	}

	@Override
	public Response updateNote(Note note, Long noteId) {
		UpdateRequest updateRequest = new UpdateRequest(INDEX, TYPE, String.valueOf(noteId)).fetchSource(true);

		try {
			String notes = objectMapper.writeValueAsString(note);
			updateRequest.doc(notes, XContentType.JSON);
			UpdateResponse updateResponse = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
			updateResponse.getGetResult().sourceAsMap();
			return ResponseHelper.statusResponse(200, "Note updated");
		} catch (JsonProcessingException e) {
			e.getMessage();
		} catch (java.io.IOException e) {
			e.getLocalizedMessage();
		}
		return ResponseHelper.statusResponse(200, "Unable to update note");
	}

	@Override
	public Response deleteNote(Long noteId) {
		DeleteRequest deleteRequest = new DeleteRequest(INDEX, TYPE, String.valueOf(noteId));
		try {
			restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
		} catch (java.io.IOException e) {
			e.getLocalizedMessage();
		}
		return ResponseHelper.statusResponse(200, "Note is deleted");
	}

	@Override
	public List<Note> searchNote(String token, String title) throws IOException {
		
		System.out.println(title); 
		
		Long id = tokenUtil.decodeToken(token);
		SearchRequest searchRequest = new SearchRequest();
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		List<Note> notes = new ArrayList<Note>();
		try {
		searchSourceBuilder.query(QueryBuilders.boolQuery()
				.must(QueryBuilders.termQuery("userid", id))
				.must(QueryBuilders.queryStringQuery("*" + title + "*")
				.lenient(true)
				.field("description")
				.field("title")
				.field("color")));

		searchRequest.source(searchSourceBuilder);
		SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

		SearchHit[] searchHit = searchResponse.getHits().getHits();

		if (searchHit.length > 0)
		{
			Arrays.stream(searchHit)
					.forEach(hit -> notes
							.add(objectMapper.convertValue(hit.getSourceAsMap(), Note.class)));
		}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return notes;
	}

}
