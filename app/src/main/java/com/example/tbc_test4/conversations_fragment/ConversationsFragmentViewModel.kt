package com.example.tbc_test4.conversations_fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ConversationsFragmentViewModel : ViewModel() {

    private val _starterList: MutableList<Conversation> = mutableListOf()

    private val _itemFlow = MutableStateFlow<List<Conversation>>(emptyList())
    val itemFlow: SharedFlow<List<Conversation>> = _itemFlow.asStateFlow()

    init {
        jsonParse()
    }

    fun search(text: String) {
        viewModelScope.launch {
            _itemFlow.value = _starterList
            _itemFlow.value = _itemFlow.value.toMutableList().apply {
                for (i in _itemFlow.value) {
                    if (!i.owner.contains(text)) {
                        remove(i)
                    }
                }
            }
        }
    }

    private fun jsonParse() {
        viewModelScope.launch {
            _itemFlow.value = _itemFlow.value.toMutableList().apply {
                val json = "[\n" +
                        "   {\n" +
                        "      \"id\":1,\n" +
                        "      \"image\":\"https://www.alia.ge/wp-content/uploads/2022/09/grisha.jpg\",\n" +
                        "      \"owner\":\"გრიშა ონიანი\",\n" +
                        "      \"last_message\":\"თავის ტერიტორიას ბომბავდა\",\n" +
                        "      \"last_active\":\"4:20 PM\",\n" +
                        "      \"unread_messages\":3,\n" +
                        "      \"is_typing\":false,\n" +
                        "      \"laste_message_type\":\"text\"\n" +
                        "   },\n" +
                        "   {\n" +
                        "      \"id\":2,\n" +
                        "      \"image\":null,\n" +
                        "      \"owner\":\"ჯემალ კაკაურიძე\",\n" +
                        "      \"last_message\":\"შემოგევლე\",\n" +
                        "      \"last_active\":\"3:00 AM\",\n" +
                        "      \"unread_messages\":0,\n" +
                        "      \"is_typing\":true,\n" +
                        "      \"laste_message_type\":\"voice\"\n" +
                        "   },\n" +
                        "   {\n" +
                        "      \"id\":3,\n" +
                        "      \"image\":\"https://i.ytimg.com/vi/KYY0TBqTfQg/hqdefault.jpg\",\n" +
                        "      \"owner\":\"გურამ ჯინორია\",\n" +
                        "      \"last_message\":\"ცოცხალი ვარ მა რა ვარ შე.. როდის იყო კვტარი ტელეფონზე ლაპარაკობდა\",\n" +
                        "      \"last_active\":\"1:00 \",\n" +
                        "      \"unread_messages\":0,\n" +
                        "      \"is_typing\":false,\n" +
                        "      \"laste_message_type\":\"file\"\n" +
                        "   },\n" +
                        "   {\n" +
                        "      \"id\":4,\n" +
                        "      \"image\":\"\",\n" +
                        "      \"owner\":\"კაკო წენგუაშვილი\",\n" +
                        "      \"last_message\":\"ადამიანი რო მოსაკლავად გაგიმეტებს თანაც ქალი ის დასანდობი არ არი\",\n" +
                        "      \"last_active\":\"1:00 PM\",\n" +
                        "      \"unread_messages\":0,\n" +
                        "      \"is_typing\":false,\n" +
                        "      \"laste_message_type\":\"text\"\n" +
                        "   }\n" +
                        "]\n"
                val moshi = Moshi.Builder()
                    .add(KotlinJsonAdapterFactory())
                    .build()

                val listType =
                    Types.newParameterizedType(List::class.java, Conversation::class.java)
                val adapter: JsonAdapter<List<Conversation>> = moshi.adapter(listType)

                for (i in adapter.fromJson(json)!!) {
                    add(i)
                }
            }
            for (i in _itemFlow.value) {
                _starterList.add(i)
            }
        }
    }
}