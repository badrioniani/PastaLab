package com.example.pastaorderapp.features.chooseOrder

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pastaorderapp.R
import com.example.pastaorderapp.data.CreateOrderSettings
import com.example.pastaorderapp.data.Guests
import com.example.pastaorderapp.data.Item
import com.example.pastaorderapp.data.Modifier
import com.example.pastaorderapp.data.Order
import com.example.pastaorderapp.data.OrderRequest
import com.example.pastaorderapp.databinding.FragmentChooseOrderBinding
import com.example.pastaorderapp.features.ExampleViewModel
import com.example.pastaorderapp.features.adapter.IngredientAdapter
import com.example.pastaorderapp.features.adapter.OrdersAdapter
import com.example.pastaorderapp.features.adapter.PastaAdapter
import com.example.pastaorderapp.features.adapter.SauceAdapter
import com.example.pastaorderapp.features.model.FinalPasta
import com.example.pastaorderapp.features.model.Ingredient
import com.example.pastaorderapp.features.model.OrderModel
import com.example.pastaorderapp.features.model.PastaType
import com.example.pastaorderapp.features.model.Sauce
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChooseOrderFragment : Fragment() {
    private lateinit var rvPasta: RecyclerView
    private lateinit var rvSauces: RecyclerView
    private lateinit var rvIngredients: RecyclerView

    private lateinit var pastaAdapter: PastaAdapter
    private lateinit var sauceAdapter: SauceAdapter
    private lateinit var ingredientAdapter: IngredientAdapter

    private val selectedDishes = mutableListOf<OrderModel>()
    private var selectedPasta: String? = null
    private var selectedPastaType: PastaType? = null
    private var selectedSauce: String? = null
    private var selectedSauceType: Sauce? = null
    private var costumerFinalOrder: ArrayList<Item>? = null
    var selectedIngredients : List<Modifier>?= null
    private var finalPasta: ArrayList<FinalPasta>? = null
    private val allSauces = listOf(
        Sauce("პესტო", img = R.drawable.pesto_souse, "რიგატონი",orderId = "05271bb0-c018-4b5b-83c7-cef18eaebe2a",price = 11.000000000),
        Sauce("კაჩიო პეპე", img = R.drawable.kachio_pepe_sousi, "რიგატონი",orderId = "06040f59-9d1e-47c4-8621-922b21bbfa64",price = 11.000000000),
        Sauce("ალ პომიდორი", img = R.drawable.al_pomidoro_sousi, "რიგატონი",orderId = "85bc1b75-13c5-4d2d-aa23-4c2d635ff159",price = 10.000000000),
        Sauce("კარბონარა", img = R.drawable.karbonara_sousi, "რიგატონი",orderId = "8ec88c04-ee00-4a6a-8c9c-a4acedf4e0b7",price = 13.000000000),
        Sauce("ბლუ ჩიზი", img = R.drawable.kachio_pepe_sousi, "რიგატონი",orderId = "ec63ff7c-9059-4faf-94a5-14d750193447",price = 12.000000000),

        Sauce("პესტო", img = R.drawable.pesto_souse, "პენე",orderId = "05271bb0-c018-4b5b-83c7-cef18eaebe2a",price = 11.000000000),
        Sauce("კაჩიო პეპე", img = R.drawable.kachio_pepe_sousi, "პენე",orderId = "06040f59-9d1e-47c4-8621-922b21bbfa64",price = 11.000000000),
        Sauce("ალ პომიდორი", img = R.drawable.al_pomidoro_sousi, "პენე",orderId = "85bc1b75-13c5-4d2d-aa23-4c2d635ff159",price = 10.000000000),
        Sauce("კარბონარა", img = R.drawable.karbonara_sousi, "პენე",orderId = "8ec88c04-ee00-4a6a-8c9c-a4acedf4e0b7",price = 13.000000000),
        Sauce("ბლუ ჩიზი", img = R.drawable.kachio_pepe_sousi, "პენე",orderId = "ec63ff7c-9059-4faf-94a5-14d750193447",price = 12.000000000),
        Sauce("სოკოს პასტა\nტრიუფელით", img = R.drawable.sokos_pasta_sousi, "პენე", orderId = "2611f301-c91c-41fb-b3d2-0588e1f346c0", price = 14.000000000),

        Sauce("პესტო", img = R.drawable.pesto_souse, "ტალიატელე",orderId = "05271bb0-c018-4b5b-83c7-cef18eaebe2a",price = 11.000000000),
        Sauce("ალ პომიდორი", img = R.drawable.al_pomidoro_sousi, "ტალიატელე",orderId = "85bc1b75-13c5-4d2d-aa23-4c2d635ff159",price = 10.000000000),
        Sauce("კარბონარა", img = R.drawable.karbonara_sousi, "ტალიატელე",orderId = "8ec88c04-ee00-4a6a-8c9c-a4acedf4e0b7",price = 13.000000000),
        Sauce("ბლუ ჩიზი", img = R.drawable.kachio_pepe_sousi, "ტალიატელე",orderId = "ec63ff7c-9059-4faf-94a5-14d750193447",price = 12.000000000),
        Sauce("სოკოს პასტა\nტრიუფელით", img = R.drawable.sokos_pasta_sousi, "ტალიატელე" ,orderId = "2611f301-c91c-41fb-b3d2-0588e1f346c0", price = 14.000000000),
        Sauce("ბოლონეზე", img = R.drawable.boloneza_sousi, "ტალიატელე", orderId = "8d5998a8-834c-46a4-9c8c-0e30c6f76936", price = 13.000000000),
        Sauce("ზღვის პროდუქტები", img = R.drawable.zghvis_produqtebis_spusi, "ტალიატელე", orderId = "3a6582d2-ff98-454f-ba7c-8ce2fc9aa026", price = 15.000000000),

        Sauce("პესტო", img = R.drawable.pesto_souse, "სპაგეტი",orderId = "05271bb0-c018-4b5b-83c7-cef18eaebe2a",price = 11.000000000),
        Sauce("ალ პომიდორი", img = R.drawable.al_pomidoro_sousi, "სპაგეტი",orderId = "85bc1b75-13c5-4d2d-aa23-4c2d635ff159",price = 10.000000000),
        Sauce("კარბონარა", img = R.drawable.karbonara_sousi, "სპაგეტი",orderId = "8ec88c04-ee00-4a6a-8c9c-a4acedf4e0b7",price = 13.000000000),
        Sauce("ბლუ ჩიზი", img = R.drawable.kachio_pepe_sousi, "სპაგეტი",orderId = "ec63ff7c-9059-4faf-94a5-14d750193447",price = 12.000000000),
        Sauce("სოკოს პასტა\nტრიუფელით", img = R.drawable.sokos_pasta_sousi, "სპაგეტი", orderId = "2611f301-c91c-41fb-b3d2-0588e1f346c0", price = 14.000000000),
        Sauce("ბოლონეზე", img = R.drawable.boloneza_sousi, "სპაგეტი",orderId = "8d5998a8-834c-46a4-9c8c-0e30c6f76936", price = 13.000000000),
        Sauce("ზღვის პროდუქტები", img = R.drawable.zghvis_produqtebis_spusi, "სპაგეტი", orderId = "3a6582d2-ff98-454f-ba7c-8ce2fc9aa026", price = 15.000000000),
    )

    private val allIngredients = listOf(
        Ingredient("ზეთის ხილი", img = R.drawable.zetis_xili, "რიგატონი", "პესტო",orderId = "05f09346-08da-4e35-9dc2-a219dffe1350",price = 1.000000000),
        Ingredient("კაპერსი", img = R.drawable.kapersi, "რიგატონი", "პესტო",orderId = "d27a2064-ab60-4536-a23a-c010200dcb54",price = 1.500000000),
        Ingredient("პომიდვრის ჩირი", img = R.drawable.cheri_pomidori, "რიგატონი", "პესტო",orderId = "871ddf19-03bb-4e49-8239-2632a9695cde",price = 1.500000000),
        Ingredient("პარმაჰემი", img = R.drawable.parmahemi, "რიგატონი", "პესტო",orderId = "ba81e6ff-12f8-4fc8-ad87-f83b95ed3d31",price = 7.000000000),
        Ingredient("სტრაჩატელა", img = R.drawable.strachatela, "რიგატონი", "პესტო",orderId = "a6e571e1-debb-43a2-8118-d016a3bd229d",price = 4.500000000),
        Ingredient("პარმეზანი", img = R.drawable.parmezani, "რიგატონი", "პესტო", orderId = "56fd67e3-522c-4fde-a210-43226454c0f1", price = 3.000000000),
        Ingredient("მობრაწული ჩერი", img = R.drawable.mobrawuli_cheri, "რიგატონი", "პესტო",orderId = "5d9c0c1f-677e-471e-b7af-00e2a76a1f22",price = 2.000000000),

        Ingredient("ბაზილიკი", img = R.drawable.bazilika, "რიგატონი", "კაჩიო პეპე", orderId = "20e20922-ceb3-48bd-8a7a-96d809754201", price = 2.000000000),
        Ingredient("ზეთის ხილი", img = R.drawable.zetis_xili, "რიგატონი", "კაჩიო პეპე",orderId = "05f09346-08da-4e35-9dc2-a219dffe1350",price = 1.000000000),
        Ingredient("კაპერსი", img = R.drawable.kapersi, "რიგატონი", "კაჩიო პეპე",orderId = "d27a2064-ab60-4536-a23a-c010200dcb54",price = 1.500000000),

        Ingredient("ბეკონი", img = R.drawable.bekon, "რიგატონი", "ალ პომიდორი", orderId = "81042a6d-e619-41d0-a7ad-2ee8c9ccb80b", price = 6.000000000),
        Ingredient("ზეთის ხილი", img = R.drawable.zetis_xili, "რიგატონი", "ალ პომიდორი",orderId = "05f09346-08da-4e35-9dc2-a219dffe1350",price = 1.000000000),
        Ingredient("კაპერსი", img = R.drawable.kapersi, "რიგატონი", "ალ პომიდორი",orderId = "d27a2064-ab60-4536-a23a-c010200dcb54",price = 1.500000000),
        Ingredient("სტრაჩატელა", img = R.drawable.strachatela, "რიგატონი", "ალ პომიდორი",orderId = "a6e571e1-debb-43a2-8118-d016a3bd229d",price = 4.500000000),
        Ingredient("პარმეზანი", img = R.drawable.parmezani, "რიგატონი", "ალ პომიდორი", orderId = "56fd67e3-522c-4fde-a210-43226454c0f1", price = 3.000000000),
        Ingredient("ბლუ ჩიზი", img = R.drawable.bekon, "რიგატონი", "ალ პომიდორი",orderId = "ec63ff7c-9059-4faf-94a5-14d750193447",price = 12.000000000),

        Ingredient("კიმჩის სოუსი", img = R.drawable.kimchis_sousi, "რიგატონი", "კარბონარა", orderId = "e0318de0-d2ca-4adb-b800-f3efe3b4b91b", price = 12.000000000),
        Ingredient("ბეკონი", img = R.drawable.bekon, "რიგატონი", "კარბონარა", orderId = "81042a6d-e619-41d0-a7ad-2ee8c9ccb80b", price = 6.000000000),
        Ingredient("პარმეზანი", img = R.drawable.parmezani, "რიგატონი", "კარბონარა", orderId = "56fd67e3-522c-4fde-a210-43226454c0f1", price = 3.000000000),
        Ingredient("ბაზილიკი", img = R.drawable.bazilika, "რიგატონი", "კარბონარა", orderId = "20e20922-ceb3-48bd-8a7a-96d809754201", price = 2.000000000),
        Ingredient("პომიდვრის ჩირი", img = R.drawable.cheri_pomidori, "რიგატონი", "კარბონარა",orderId = "871ddf19-03bb-4e49-8239-2632a9695cde",price = 1.500000000),
        Ingredient("პეკორინო", img = R.drawable.cheri_pomidori, "რიგატონი", "კარბონარა", orderId = "d9b14f81-c23c-4855-9a6f-67341c0d8145", price = 3.00000000),

        Ingredient("ბაზილიკი", img = R.drawable.bazilika, "რიგატონი", "ბლუ ჩიზი", orderId = "20e20922-ceb3-48bd-8a7a-96d809754201", price = 2.000000000),
        Ingredient("პომიდვრის ჩირი", img = R.drawable.cheri_pomidori, "რიგატონი", "ბლუ ჩიზი",orderId = "871ddf19-03bb-4e49-8239-2632a9695cde",price = 1.500000000),
        Ingredient("ზეთის ხილი", img = R.drawable.zetis_xili, "რიგატონი", "ბლუ ჩიზი",orderId = "05f09346-08da-4e35-9dc2-a219dffe1350",price = 1.000000000),
        Ingredient("კაპერსი", img = R.drawable.kapersi, "რიგატონი", "ბლუ ჩიზი",orderId = "d27a2064-ab60-4536-a23a-c010200dcb54",price = 1.500000000),
        Ingredient("ბეკონი", img = R.drawable.bekon, "რიგატონი", "ბლუ ჩიზი", orderId = "81042a6d-e619-41d0-a7ad-2ee8c9ccb80b", price = 6.000000000),

        Ingredient("ზეთის ხილი", img = R.drawable.zetis_xili, "პენე", "პესტო",orderId = "05f09346-08da-4e35-9dc2-a219dffe1350",price = 1.000000000),
        Ingredient("კაპერსი", img = R.drawable.kapersi, "პენე", "პესტო",orderId = "d27a2064-ab60-4536-a23a-c010200dcb54",price = 1.500000000),
        Ingredient("პომიდვრის ჩირი", img = R.drawable.cheri_pomidori, "პენე", "პესტო",orderId = "871ddf19-03bb-4e49-8239-2632a9695cde",price = 1.500000000),
        Ingredient("პარმაჰემი", img = R.drawable.parmahemi, "პენე", "პესტო",orderId = "ba81e6ff-12f8-4fc8-ad87-f83b95ed3d31",price = 7.000000000),
        Ingredient("სტრაჩატელა", img = R.drawable.strachatela, "პენე", "პესტო",orderId = "a6e571e1-debb-43a2-8118-d016a3bd229d",price = 4.500000000),
        Ingredient("პარმეზანი", img = R.drawable.parmezani, "პენე", "პესტო", orderId = "56fd67e3-522c-4fde-a210-43226454c0f1", price = 3.000000000),
        Ingredient("მობრაწული ჩერი", img = R.drawable.mobrawuli_cheri, "პენე", "პესტო",orderId = "5d9c0c1f-677e-471e-b7af-00e2a76a1f22",price = 2.000000000),

        Ingredient("ბაზილიკი", img = R.drawable.bazilika, "პენე", "კაჩიო პეპე", orderId = "20e20922-ceb3-48bd-8a7a-96d809754201", price = 2.000000000),
        Ingredient("ზეთის ხილი", img = R.drawable.zetis_xili, "პენე", "კაჩიო პეპე",orderId = "05f09346-08da-4e35-9dc2-a219dffe1350",price = 1.000000000),
        Ingredient("კაპერსი", img = R.drawable.kapersi, "პენე", "კაჩიო პეპე",orderId = "d27a2064-ab60-4536-a23a-c010200dcb54",price = 1.500000000),

        Ingredient("ბეკონი", img = R.drawable.bekon, "პენე", "ალ პომიდორი", orderId = "81042a6d-e619-41d0-a7ad-2ee8c9ccb80b", price = 6.000000000),
        Ingredient("ზეთის ხილი", img = R.drawable.zetis_xili, "პენე", "ალ პომიდორი",orderId = "05f09346-08da-4e35-9dc2-a219dffe1350",price = 1.000000000),
        Ingredient("კაპერსი", img = R.drawable.kapersi, "პენე", "ალ პომიდორი",orderId = "d27a2064-ab60-4536-a23a-c010200dcb54",price = 1.500000000),
        Ingredient("სტრაჩატელა", img = R.drawable.strachatela, "პენე", "ალ პომიდორი",orderId = "a6e571e1-debb-43a2-8118-d016a3bd229d",price = 4.500000000),
        Ingredient("პარმეზანი", img = R.drawable.parmezani, "პენე", "ალ პომიდორი", orderId = "56fd67e3-522c-4fde-a210-43226454c0f1", price = 3.000000000),
        Ingredient("ბლუ ჩიზი", img = R.drawable.parmezani, "პენე", "ალ პომიდორი",orderId = "ec63ff7c-9059-4faf-94a5-14d750193447",price = 12.000000000),

        Ingredient("2X სოკო", img = R.drawable.soko, "პენე", "სოკოს პასტა\nტრიუფელით", orderId = "2765ecea-873b-4381-a84a-43d5b251f56f", price = 3.000000000),
        Ingredient("ბეკონი", img = R.drawable.bekon, "პენე", "სოკოს პასტა\nტრიუფელით", orderId = "81042a6d-e619-41d0-a7ad-2ee8c9ccb80b", price = 6.000000000),
        Ingredient("კაპერსი", img = R.drawable.kapersi, "პენე", "სოკოს პასტა\nტრიუფელით",orderId = "d27a2064-ab60-4536-a23a-c010200dcb54",price = 1.500000000),
        Ingredient("პარმეზანი", img = R.drawable.parmezani, "პენე", "სოკოს პასტა\nტრიუფელით", orderId = "56fd67e3-522c-4fde-a210-43226454c0f1", price = 3.000000000),

        Ingredient("ბაზილიკი", img = R.drawable.bazilika, "პენე", "ბლუ ჩიზი", orderId = "20e20922-ceb3-48bd-8a7a-96d809754201", price = 2.000000000),
        Ingredient("პომიდვრის ჩირი", img = R.drawable.cheri_pomidori, "პენე", "ბლუ ჩიზი",orderId = "871ddf19-03bb-4e49-8239-2632a9695cde",price = 1.500000000),
        Ingredient("ზეთის ხილი", img = R.drawable.zetis_xili, "პენე", "ბლუ ჩიზი",orderId = "05f09346-08da-4e35-9dc2-a219dffe1350",price = 1.000000000),
        Ingredient("კაპერსი", img = R.drawable.kapersi, "პენე", "ბლუ ჩიზი",orderId = "d27a2064-ab60-4536-a23a-c010200dcb54",price = 1.500000000),
        Ingredient("ბეკონი", img = R.drawable.bekon, "პენე", "ბლუ ჩიზი", orderId = "81042a6d-e619-41d0-a7ad-2ee8c9ccb80b", price = 6.000000000),

        Ingredient("ზეთის ხილი", img = R.drawable.zetis_xili, "ტალიატელე", "პესტო",orderId = "05f09346-08da-4e35-9dc2-a219dffe1350",price = 1.000000000),
        Ingredient("კაპერსი", img = R.drawable.kapersi, "ტალიატელე", "პესტო",orderId = "d27a2064-ab60-4536-a23a-c010200dcb54",price = 1.500000000),
        Ingredient("პომიდვრის ჩირი", img = R.drawable.cheri_pomidori, "ტალიატელე", "პესტო",orderId = "871ddf19-03bb-4e49-8239-2632a9695cde",price = 1.500000000),
        Ingredient("პარმაჰემი", img = R.drawable.parmahemi, "ტალიატელე", "პესტო",orderId = "ba81e6ff-12f8-4fc8-ad87-f83b95ed3d31",price = 7.000000000),
        Ingredient("სტრაჩატელა", img = R.drawable.strachatela, "ტალიატელე", "პესტო",orderId = "a6e571e1-debb-43a2-8118-d016a3bd229d",price = 4.500000000),
        Ingredient("პარმეზანი", img = R.drawable.parmezani, "ტალიატელე", "პესტო", orderId = "56fd67e3-522c-4fde-a210-43226454c0f1", price = 3.000000000),
        Ingredient("მობრაწული ჩერი", img = R.drawable.mobrawuli_cheri, "ტალიატელე", "პესტო",orderId = "5d9c0c1f-677e-471e-b7af-00e2a76a1f22",price = 2.000000000),

        Ingredient("ბეკონი", img = R.drawable.bekon, "ტალიატელე", "ალ პომიდორი", orderId = "81042a6d-e619-41d0-a7ad-2ee8c9ccb80b", price = 6.000000000),
        Ingredient("ზეთის ხილი", img = R.drawable.zetis_xili, "ტალიატელე", "ალ პომიდორი",orderId = "05f09346-08da-4e35-9dc2-a219dffe1350",price = 1.000000000),
        Ingredient("კაპერსი", img = R.drawable.kapersi, "ტალიატელე", "ალ პომიდორი",orderId = "d27a2064-ab60-4536-a23a-c010200dcb54",price = 1.500000000),
        Ingredient("სტრაჩატელა", img = R.drawable.strachatela, "ტალიატელე", "ალ პომიდორი",orderId = "a6e571e1-debb-43a2-8118-d016a3bd229d",price = 4.500000000),
        Ingredient("პარმეზანი", img = R.drawable.parmezani, "ტალიატელე", "ალ პომიდორი", orderId = "56fd67e3-522c-4fde-a210-43226454c0f1", price = 3.000000000),
        Ingredient("ბლუ ჩიზი", img = R.drawable.parmezani, "ტალიატელე", "ალ პომიდორი",orderId = "ec63ff7c-9059-4faf-94a5-14d750193447",price = 12.000000000),

        Ingredient("2X სოკო", img = R.drawable.soko, "ტალიატელე", "სოკოს პასტა\nტრიუფელით", orderId = "2765ecea-873b-4381-a84a-43d5b251f56f", price = 3.000000000),
        Ingredient("ბეკონი", img = R.drawable.bekon, "ტალიატელე", "სოკოს პასტა\nტრიუფელით", orderId = "81042a6d-e619-41d0-a7ad-2ee8c9ccb80b", price = 6.000000000),
        Ingredient("კაპერსი", img = R.drawable.kapersi, "ტალიატელე", "სოკოს პასტა\nტრიუფელით",orderId = "d27a2064-ab60-4536-a23a-c010200dcb54",price = 1.500000000),
        Ingredient("პარმეზანი", img = R.drawable.parmezani, "ტალიატელე", "სოკოს პასტა\nტრიუფელით", orderId = "56fd67e3-522c-4fde-a210-43226454c0f1", price = 3.000000000),

        Ingredient("კიმჩის სოუსი", img = R.drawable.kimchis_sousi, "ტალიატელე", "კარბონარა", orderId = "e0318de0-d2ca-4adb-b800-f3efe3b4b91b", price = 12.000000000),
        Ingredient("ბეკონი", img = R.drawable.bekon, "ტალიატელე", "კარბონარა", orderId = "81042a6d-e619-41d0-a7ad-2ee8c9ccb80b", price = 6.000000000),
        Ingredient("პარმეზანი", img = R.drawable.parmezani, "ტალიატელე", "კარბონარა", orderId = "56fd67e3-522c-4fde-a210-43226454c0f1", price = 3.000000000),
        Ingredient("ბაზილიკი", img = R.drawable.bazilika, "ტალიატელე", "კარბონარა", orderId = "20e20922-ceb3-48bd-8a7a-96d809754201", price = 2.000000000),
        Ingredient("პომიდვრის ჩირი", img = R.drawable.cheri_pomidori, "ტალიატელე", "კარბონარა",orderId = "871ddf19-03bb-4e49-8239-2632a9695cde",price = 1.500000000),
        Ingredient("პეკორინო", img = R.drawable.cheri_pomidori, "ტალიატელე", "კარბონარა", orderId = "d9b14f81-c23c-4855-9a6f-67341c0d8145", price = 3.00000000),

        Ingredient("ბაზილიკი", img = R.drawable.bazilika, "ტალიატელე", "ბლუ ჩიზი", orderId = "20e20922-ceb3-48bd-8a7a-96d809754201", price = 2.000000000),
        Ingredient("პომიდვრის ჩირი", img = R.drawable.cheri_pomidori, "ტალიატელე", "ბლუ ჩიზი",orderId = "871ddf19-03bb-4e49-8239-2632a9695cde",price = 1.500000000),
        Ingredient("ზეთის ხილი", img = R.drawable.zetis_xili, "ტალიატელე", "ბლუ ჩიზი",orderId = "05f09346-08da-4e35-9dc2-a219dffe1350",price = 1.000000000),
        Ingredient("კაპერსი", img = R.drawable.kapersi, "ტალიატელე", "ბლუ ჩიზი",orderId = "d27a2064-ab60-4536-a23a-c010200dcb54",price = 1.500000000),
        Ingredient("ბეკონი", img = R.drawable.bekon, "ტალიატელე", "ბლუ ჩიზი", orderId = "81042a6d-e619-41d0-a7ad-2ee8c9ccb80b", price = 6.000000000),

        Ingredient("ნაღები", img = R.drawable.bekon, "ტალიატელე", "ბოლონეზე", orderId = "7f84182b-ad22-49f7-99da-8ecfac1f1bc3", price = 2.500000000),
        Ingredient("პომიდვრის ჩირი", img = R.drawable.cheri_pomidori, "ტალიატელე", "ბოლონეზე",orderId = "871ddf19-03bb-4e49-8239-2632a9695cde",price = 1.500000000),
        Ingredient("კაპერსი", img = R.drawable.kapersi, "ტალიატელე", "ბოლონეზე",orderId = "d27a2064-ab60-4536-a23a-c010200dcb54",price = 1.500000000),
        Ingredient("ზეთის ხილი", img = R.drawable.zetis_xili, "ტალიატელე", "ბოლონეზე",orderId = "05f09346-08da-4e35-9dc2-a219dffe1350",price = 1.000000000),
        Ingredient("ჰალაპენიო", img = R.drawable.halapenio, "ტალიატელე", "ბოლონეზე", orderId = "0817a313-9ae9-441b-ac7c-195f91dd7631", price = 1.500000000),
        Ingredient("პარმეზანი", img = R.drawable.parmezani, "ტალიატელე", "ბოლონეზე", orderId = "56fd67e3-522c-4fde-a210-43226454c0f1", price = 3.000000000),

        Ingredient("2X ზღვის პროდუქტი", img = R.drawable.zghvis_produqtebis_spusi, "ტალიატელე", "ზღვის პროდუქტები", orderId = "3a6582d2-ff98-454f-ba7c-8ce2fc9aa026", price = 15.000000000),
        Ingredient("პომიდვრის ჩირი", img = R.drawable.cheri_pomidori, "ტალიატელე", "ზღვის პროდუქტები",orderId = "871ddf19-03bb-4e49-8239-2632a9695cde",price = 1.500000000),
        Ingredient("ზეთის ხილი", img = R.drawable.zetis_xili, "ტალიატელე", "ზღვის პროდუქტები",orderId = "05f09346-08da-4e35-9dc2-a219dffe1350",price = 1.000000000),
        Ingredient("კაპერსი", img = R.drawable.kapersi, "ტალიატელე", "ზღვის პროდუქტები",orderId = "d27a2064-ab60-4536-a23a-c010200dcb54",price = 1.500000000),

        Ingredient("ზეთის ხილი", img = R.drawable.zetis_xili, "სპაგეტი", "პესტო",orderId = "05f09346-08da-4e35-9dc2-a219dffe1350",price = 1.000000000),
        Ingredient("კაპერსი", img = R.drawable.kapersi, "სპაგეტი", "პესტო",orderId = "d27a2064-ab60-4536-a23a-c010200dcb54",price = 1.500000000),
        Ingredient("პომიდვრის ჩირი", img = R.drawable.cheri_pomidori, "სპაგეტი", "პესტო",orderId = "871ddf19-03bb-4e49-8239-2632a9695cde",price = 1.500000000),
        Ingredient("პარმაჰემი", img = R.drawable.parmahemi, "სპაგეტი", "პესტო",orderId = "ba81e6ff-12f8-4fc8-ad87-f83b95ed3d31",price = 7.000000000),
        Ingredient("სტრაჩატელა", img = R.drawable.strachatela, "სპაგეტი", "პესტო",orderId = "a6e571e1-debb-43a2-8118-d016a3bd229d",price = 4.500000000),
        Ingredient("პარმეზანი", img = R.drawable.parmezani, "სპაგეტი", "პესტო", orderId = "56fd67e3-522c-4fde-a210-43226454c0f1", price = 3.000000000),
        Ingredient("მობრაწული ჩერი", img = R.drawable.mobrawuli_cheri, "სპაგეტი", "პესტო",orderId = "5d9c0c1f-677e-471e-b7af-00e2a76a1f22",price = 2.000000000),

        Ingredient("ბეკონი", img = R.drawable.bekon, "სპაგეტი", "ალ პომიდორი", orderId = "81042a6d-e619-41d0-a7ad-2ee8c9ccb80b", price = 6.000000000),
        Ingredient("ზეთის ხილი", img = R.drawable.zetis_xili, "სპაგეტი", "ალ პომიდორი",orderId = "05f09346-08da-4e35-9dc2-a219dffe1350",price = 1.000000000),
        Ingredient("კაპერსი", img = R.drawable.kapersi, "სპაგეტი", "ალ პომიდორი",orderId = "d27a2064-ab60-4536-a23a-c010200dcb54",price = 1.500000000),
        Ingredient("სტრაჩატელა", img = R.drawable.strachatela, "სპაგეტი", "ალ პომიდორი",orderId = "a6e571e1-debb-43a2-8118-d016a3bd229d",price = 4.500000000),
        Ingredient("პარმეზანი", img = R.drawable.parmezani, "სპაგეტი", "ალ პომიდორი", orderId = "56fd67e3-522c-4fde-a210-43226454c0f1", price = 3.000000000),
        Ingredient("ბლუ ჩიზი", img = R.drawable.parmezani, "სპაგეტი", "ალ პომიდორი",orderId = "ec63ff7c-9059-4faf-94a5-14d750193447",price = 12.000000000),

        Ingredient("2X სოკო", img = R.drawable.soko, "სპაგეტი", "სოკოს პასტა\nტრიუფელით", orderId = "2765ecea-873b-4381-a84a-43d5b251f56f", price = 3.000000000),
        Ingredient("ბეკონი", img = R.drawable.bekon, "სპაგეტი", "სოკოს პასტა\nტრიუფელით", orderId = "81042a6d-e619-41d0-a7ad-2ee8c9ccb80b", price = 6.000000000),
        Ingredient("კაპერსი", img = R.drawable.kapersi, "სპაგეტი", "სოკოს პასტა\nტრიუფელით",orderId = "d27a2064-ab60-4536-a23a-c010200dcb54",price = 1.500000000),
        Ingredient("პარმეზანი", img = R.drawable.parmezani, "სპაგეტი", "სოკოს პასტა\nტრიუფელით", orderId = "56fd67e3-522c-4fde-a210-43226454c0f1", price = 3.000000000),

        Ingredient("კიმჩის სოუსი", img = R.drawable.kimchis_sousi, "სპაგეტი", "კარბონარა", orderId = "e0318de0-d2ca-4adb-b800-f3efe3b4b91b", price = 12.000000000),
        Ingredient("ბეკონი", img = R.drawable.bekon, "სპაგეტი", "კარბონარა", orderId = "81042a6d-e619-41d0-a7ad-2ee8c9ccb80b", price = 6.000000000),
        Ingredient("პარმეზანი", img = R.drawable.parmezani, "სპაგეტი", "კარბონარა", orderId = "56fd67e3-522c-4fde-a210-43226454c0f1", price = 3.000000000),
        Ingredient("ბაზილიკი", img = R.drawable.bazilika, "სპაგეტი", "კარბონარა", orderId = "20e20922-ceb3-48bd-8a7a-96d809754201", price = 2.000000000),
        Ingredient("პომიდვრის ჩირი", img = R.drawable.cheri_pomidori, "სპაგეტი", "კარბონარა",orderId = "871ddf19-03bb-4e49-8239-2632a9695cde",price = 1.500000000),
        Ingredient("ბეკონირო", img = R.drawable.bekon, "სპაგეტი", "კარბონარა", orderId = "d9b14f81-c23c-4855-9a6f-67341c0d8145", price = 3.00000000),

        Ingredient("ბაზილიკი", img = R.drawable.bazilika, "სპაგეტი", "ბლუ ჩიზი", orderId = "20e20922-ceb3-48bd-8a7a-96d809754201", price = 2.000000000),
        Ingredient("პომიდვრის ჩირი", img = R.drawable.cheri_pomidori, "სპაგეტი", "ბლუ ჩიზი",orderId = "871ddf19-03bb-4e49-8239-2632a9695cde",price = 1.500000000),
        Ingredient("ზეთის ხილი", img = R.drawable.zetis_xili, "სპაგეტი", "ბლუ ჩიზი",orderId = "05f09346-08da-4e35-9dc2-a219dffe1350",price = 1.000000000),
        Ingredient("კაპერსი", img = R.drawable.kapersi, "სპაგეტი", "ბლუ ჩიზი",orderId = "d27a2064-ab60-4536-a23a-c010200dcb54",price = 1.500000000),
        Ingredient("ბეკონი", img = R.drawable.bekon, "სპაგეტი", "ბლუ ჩიზი", orderId = "81042a6d-e619-41d0-a7ad-2ee8c9ccb80b", price = 6.000000000),

        Ingredient("ნაღები", img = R.drawable.bekon, "სპაგეტი", "ბოლონეზე"),
        Ingredient("პომიდვრის ჩირი", img = R.drawable.cheri_pomidori, "სპაგეტი", "ბოლონეზე",orderId = "871ddf19-03bb-4e49-8239-2632a9695cde",price = 1.500000000),
        Ingredient("კაპერსი", img = R.drawable.kapersi, "სპაგეტი", "ბოლონეზე",orderId = "d27a2064-ab60-4536-a23a-c010200dcb54",price = 1.500000000),
        Ingredient("ზეთის ხილი", img = R.drawable.zetis_xili, "სპაგეტი", "ბოლონეზე",orderId = "05f09346-08da-4e35-9dc2-a219dffe1350",price = 1.000000000),
        Ingredient("ჰალაპენიო", img = R.drawable.halapenio, "სპაგეტი", "ბოლონეზე", orderId = "0817a313-9ae9-441b-ac7c-195f91dd7631", price = 1.500000000),
        Ingredient("პარმეზანი", img = R.drawable.parmezani, "სპაგეტი", "ბოლონეზე", orderId = "56fd67e3-522c-4fde-a210-43226454c0f1", price = 3.000000000),

        Ingredient("2X ზღვის პროდუქტი", img = R.drawable.zghvis_produqtebis_spusi, "სპაგეტი", "ზღვის პროდუქტები", orderId = "3a6582d2-ff98-454f-ba7c-8ce2fc9aa026", price = 15.000000000),
        Ingredient("პომიდვრის ჩირი", img = R.drawable.cheri_pomidori, "სპაგეტი", "ზღვის პროდუქტები",orderId = "871ddf19-03bb-4e49-8239-2632a9695cde",price = 1.500000000),
        Ingredient("ზეთის ხილი", img = R.drawable.zetis_xili, "სპაგეტი", "ზღვის პროდუქტები",orderId = "05f09346-08da-4e35-9dc2-a219dffe1350",price = 1.000000000),
        Ingredient("კაპერსი", img = R.drawable.kapersi, "სპაგეტი", "ზღვის პროდუქტები",orderId = "d27a2064-ab60-4536-a23a-c010200dcb54",price = 1.500000000),
    )

    private val viewModel: ExampleViewModel by viewModels()
    private val binding by lazy {
        FragmentChooseOrderBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
//        finalPasta = ArrayList()
        costumerFinalOrder = arrayListOf()
        finalPasta = arrayListOf()
        selectedIngredients = arrayListOf()
        val orderList = listOf(
            OrderModel(
                id = 100,
                isTrend = true,
                name = "შექმენი შენი პასტა",
                img = R.drawable.create_your_own,
                orderId = "",
                price = 25.34,
                isFire = false
            ), OrderModel(
                id = 1,
                isTrend = false,
                orderId = "c61419c7-686a-473c-a162-ac85ee7f6f9d",
                name = "ბოლონეზა",
                img = R.drawable.boloneza,
                price = 26.800000000,
                isFire = false
            ), OrderModel(
                id = 1,
                isTrend = false,
                orderId = "ca6bcca4-9ee8-444c-8646-880ac13e3edf",
                name = "ნიოკი ქათმით და კიმჩით",
                img = R.drawable.nioki,
                price = 25.900000000,
                isFire = true
            ), OrderModel(
                id = 1,
                isTrend = false,
                orderId = "9a681600-b84f-4bbc-8093-7d64e53f2720",
                name = "რიგატონინი პესტო",
                img = R.drawable.rigatoni_pesto,
                price = 24.800000000,
                isFire = true
            ), OrderModel(
                id = 1,
                isTrend = false,
                orderId = "0b2a467b-25dd-4002-a6a2-14e66c5d1b88",
                name = "კარბონარა",
                img = R.drawable.karbonara,
                price = 26.900000000,
                isFire = false
            ), OrderModel(
                id = 1,
                isTrend = false,
                orderId = "b36a7f74-c08c-492a-8e59-db2eb960085a",
                name = "ზღვის პროდუქტების პასტა",
                img = R.drawable.zgvis_produqtebis,
                price = 28.900000000,
                isFire = false
            ), OrderModel(
                id = 1,
                isTrend = false,
                orderId = "efa6e618-5d34-4059-b820-2de9740a083d",
                name = "ალ პომიდორო",
                img = R.drawable.al_pomidoro,
                price = 24.900000000,
                isFire = false
            ), OrderModel(
                id = 1,
                isTrend = false,
                orderId = "08c41f98-7e16-49b3-b946-93c86127a096",
                name = "სოკოს პასტა ტრიუფელით",
                img = R.drawable.sokos_pasta,
                price = 27.900000000,
                isFire = false
            ), OrderModel(
                id = 1,
                isTrend = false,
                orderId = "a5630ec6-7d8e-4ca3-8ed2-0126f8799fe5",
                name = "ბლუ ჩიზის პასტა",
                img = R.drawable.lurji_yvelis_pasta,
                price = 25.700000000,
                isFire = false
            ), OrderModel(
                id = 1,
                isTrend = false,
                orderId = "759fb5b4-3014-4e74-a4ee-4528d21a851a",
                name = "ნიოკის პესტო სტრაჩატელი",
                img = R.drawable.niokis_pesto,
                price = 25.900000000,
                isFire = false
            ), OrderModel(
                id = 1,
                isTrend = false,
                orderId = "9e42ed40-be6e-4177-8601-ed5bcef7f909",
                name = "კაჩიო პეპე",
                img = R.drawable.kachio_pepe,
                price = 24.900000000,
                isFire = false
            )
        )
        val saladList = listOf(
            OrderModel(
                id = 1,
                isTrend = false,
                orderId = "8dc7284c-bdf8-4a5f-b541-6aea3e3049bf",
                name = "სალათი - კაპრეზე",
                img = R.drawable.kapreze,
                price = 17.900000000,
                isFire = false
            ), OrderModel(
                id = 1,
                isTrend = false,
                orderId = "dd4b12dc-8ba0-4ce8-8f78-0f3d16f06a32",
                name = "სალათი - ორაგულის",
                img = R.drawable.oragulis_salati,
                price = 18.900000000,
                isFire = false
            ), OrderModel(
                id = 1,
                isTrend = false,
                orderId = "2f6b4f42-2d9b-4fbb-b63b-13859f78e8e0",
                name = "სალათი - ფრეში",
                img = R.drawable.fresh_salati,
                price = 14.900000000,
                isFire = false
            )
        )

        //აქაა სასმელები
        val drinksList = listOf(
            OrderModel(
                id = 1,
                isTrend = false,
                orderId = "38a28a2c-378e-4d3b-b1c2-b21ed68bec73",
                name = "კოკა-კოლა",
                img = R.drawable.coca_cola,
                price = 2.500000000,
                isFire = false
            ), OrderModel(
                id = 1,
                isTrend = false,
                orderId = "9fd5fec9-1771-457b-9e67-e44b9edb86bc",
                name = "კაპი ფორთოხლის",
                img = R.drawable.kapi_fortoxali,
                price = 3.500000000,
                isFire = false
            )
        )
        val adapter =
            OrdersAdapter(itemList = orderList,
                onItemClick = {item, isPlus ->
                    if (item.id == 100) {
                        binding.apply {
                            chooseYourOwn.isVisible = true
                            ordersList.isVisible = false
                            binding.drinksList.isVisible = false
                            title2.isVisible = false
                            title3.isVisible = false
                            title1.isVisible = false
                            binding.saladList.isVisible = false
                            check.isVisible = false
                            createOwnOrder()
                        }
                    }
                },
                onItemCountChange = {item, isAdded ->
                    if (isAdded) {
                        selectedDishes.add(item)
                    } else {
                        selectedDishes.remove(item)
                    }
                    Log.d("SelectedItems", selectedDishes.toString())
                })


        val saladAdapter =
            OrdersAdapter(itemList = saladList,
                onItemClick = {item, isPlus ->

                },
                onItemCountChange = {item, isAdded ->
                    if (isAdded) {
                        selectedDishes.add(item)
                    } else {
                        selectedDishes.remove(item)
                    }
                    Log.d("SelectedItems", selectedDishes.toString())
                })
        val drinksAdapter =
            OrdersAdapter(itemList = drinksList,
                onItemClick = {item, isPlus ->

                },
                onItemCountChange = {item, isAdded ->

                })

        binding.ordersList.layoutManager = GridLayoutManager(requireContext(), 5)
        binding.ordersList.adapter = adapter
        binding.saladList.layoutManager = GridLayoutManager(requireContext(), 5)
        binding.saladList.adapter = saladAdapter
        binding.drinksList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.drinksList.adapter = drinksAdapter

        binding.back.setOnClickListener {
            back()
        }
        finalStep()
        return binding.root
    }
    private fun back(){
        binding.apply {
            chooseYourOwn.isVisible = false
            ordersList.isVisible = true
            binding.drinksList.isVisible = true
            title2.isVisible = true
            title3.isVisible = true
            title1.isVisible = true
            binding.saladList.isVisible = true
            check.isVisible = true
        }
    }
    private fun createOwnOrder(){

        rvPasta = binding.rvPasta
        rvSauces = binding.rvSauces
        rvIngredients = binding.rvIngredients

        rvPasta.layoutManager = GridLayoutManager(requireContext(), 4)
        rvSauces.layoutManager = GridLayoutManager(requireContext(), 5)
        rvIngredients.layoutManager = GridLayoutManager(requireContext(), 5)

        pastaAdapter = PastaAdapter(
            listOf(
                PastaType("რიგატონი", R.drawable.rigato,orderId="17e7fa3a-92d8-4b9a-827c-6d4dd968760d",price = 5.000000000),
                PastaType("პენე", R.drawable.pene,orderId="17e7fa3a-92d8-4b9a-827c-6d4dd968760d",price = 5.000000000),
                PastaType("ტალიატელე", R.drawable.taliatele,orderId="17e7fa3a-92d8-4b9a-827c-6d4dd968760d",price = 5.000000000),
                PastaType("სპაგეტი", R.drawable.spageti,orderId="35937320-b513-44be-9d0f-f1b881de63b6",price = 4.000000000),
            ),
            singleSelect = true,
            isSelected = { it.isSelected },
            setSelected = { item, selected -> item.isSelected = selected },
            onSelectionChanged = {},
            onClick = { pasta ->
                selectedPastaType = pasta
                onPastaSelected(pasta.name)
            })

        sauceAdapter = SauceAdapter(
            emptyList(),
            singleSelect = true,
            isSelected = { it.isSelected },
            setSelected = { item, selected -> item.isSelected = selected },
            onSelectionChanged = {},
            onClick = { sauce ->
                selectedSauceType = sauce
                onSauceSelected(sauce.name)
            })

        ingredientAdapter = IngredientAdapter(
            emptyList(),
            singleSelect = false,
            isSelected = { it.isSelected },
            setSelected = { item, selected -> item.isSelected = selected },
            onSelectionChanged = {},
            onClick = {
                selectedIngredients = ingredientAdapter.getSelectedIngredients()
            })

        rvPasta.adapter = pastaAdapter
        rvSauces.adapter = sauceAdapter
        rvIngredients.adapter = ingredientAdapter

        rvSauces.visibility = View.GONE
        rvIngredients.visibility = View.GONE
        binding.btnCreate.setOnClickListener {
            if (selectedPastaType != null && selectedSauceType != null && selectedIngredients != null){

                finalPasta?.add(FinalPasta(pasta = selectedPastaType!!, sauce = selectedSauceType!!,extras = selectedIngredients!!))
                Log.e("Final Pasta",finalPasta.toString())
                Toast.makeText(requireContext(),"პასტა წარმატებით შექმნა!", Toast.LENGTH_SHORT).show()
            binding.apply {
                    chooseYourOwn.isVisible = false
                    ordersList.isVisible = true
                    binding.drinksList.isVisible = true
                    title2.isVisible = true
                    title3.isVisible = true
                    title1.isVisible = true
                    binding.saladList.isVisible = true
                    check.isVisible = true
                }

            }
        }
    }

    private fun onPastaSelected(pastaName: String) {
        selectedPasta = pastaName
        selectedSauce = null

        rvSauces.visibility = View.VISIBLE
        binding.titleSouse.isVisible = true
        rvIngredients.visibility = View.GONE

        val sauces = allSauces.filter { it.forPasta == pastaName }
        sauceAdapter.updateData(sauces)
    }

    private fun onSauceSelected(sauceName: String) {
        selectedSauce = sauceName
        binding.titleIngredient.isVisible = true
        rvIngredients.visibility = View.VISIBLE
        val ingredients = allIngredients.filter {
            it.forPasta == selectedPasta && it.forSauce == sauceName
        }
        ingredientAdapter.updateData(ingredients)
    }

    private fun finalStep() {
        binding.orderButton.setOnClickListener {
            Log.e("badria",finalPasta.toString())
            finalPasta?.let { it1 ->
                if (it1.isNotEmpty()) {
                    it1.forEach { finalPastaItem ->
                        val modifiers = mutableListOf<Modifier>()

                        modifiers.add(
                            Modifier(
                                productId = finalPastaItem.sauce.orderId,
                                amount = 1,
                                productGroupId = null,
                                price = finalPastaItem.sauce.price ?: 0.00,
                                positionId = null
                            )
                        )
                        modifiers.addAll(finalPastaItem.extras)
                        Log.e("badria", modifiers.toString())
                    }
                }
            }
            AlertDialog.Builder(requireContext())
                .setTitle("დადასტურება")
                .setMessage("გსურს რომ დაადასტურო შეკვეთა?")
                .setPositiveButton("კი") { dialog, _ ->
                    // აქ ჩაწერე დაკონფირმების ლოგიკა
                    if (!selectedDishes.isEmpty()) {
                        selectedDishes.forEach { it ->
                            costumerFinalOrder?.add(
                                Item(
                                    productId = it.orderId,
                                    price = it.price ?: 0.00,
                                    comment = ""
                                )
                            )
                        }
                    }

                    finalPasta?.let { it1 ->
                        if (!it1.isNullOrEmpty()) {

                            it1.forEach { it ->
                                val modifiers = mutableListOf<Modifier>()
                                modifiers.add(Modifier(
                                        productId = it.sauce.orderId,
                                        amount = 1,
                                        productGroupId = null,
                                        price = it.sauce.price ?: 0.00,
                                        positionId = null
                                    ))
                                modifiers.addAll(it.extras)
                                costumerFinalOrder?.add(
                                    Item(
                                        productId = it.pasta.orderId,
                                        modifiers = modifiers,
                                        price = it.pasta.price ?: 0.00,
                                        comment = ""
                                    )
                                )
                            }
                        }
                    }
                    viewModel.examples.observe(viewLifecycleOwner) { examples ->
                        viewModel.sendOrder("Bearer "+examples.token, OrderRequest(
                            organizationId = "aabf3292-a24b-4eff-bd37-65381880f8b3",
                            terminalGroupId = "d5825ab1-4c05-c0bb-0196-8c970dd80066",
                            order = Order(
                                id = null,
                                externalNumber = null,
                                tableIds = null,
                                customer = null,
                                phone = null,
                                guestCount = 1,
                                guests = Guests(
                                    count = 1
                                ),
                                tabName = null,
                                menuId = null,
                                items = costumerFinalOrder?:emptyList(),
                                combos = listOf(),
                                payments = listOf(),
                                tips = listOf(),
                                sourceKey = null,
                                discountsInfo = null,
                                loyaltyInfo = null,
                                orderTypeId = null,
                                chequeAdditionalInfo = null,
                                externalData = listOf()
                            ),
                            createOrderSettings = CreateOrderSettings(
                                servicePrint = false,
                                transportToFrontTimeout = 0,
                                checkStopList = false
                            )
                        ))
                        viewModel.order.observe(viewLifecycleOwner) { order ->
                            Log.e("finalPastaaaas", "egaa")
                        }
                    }
                    Log.d("finalPastaaaas",costumerFinalOrder.toString())
                    Toast.makeText(requireContext(), "შეკვეთა დადასტურდა", Toast.LENGTH_SHORT)
                        .show()
                    AlertDialog.Builder(requireContext())
                        .setTitle("შეკვეთა")
                        .setMessage("შეკვეთა დამატებულია გთხოვთ ეწვიოთ მოლარს")
                        .setPositiveButton("დასრულება") { dialog, _ ->
                            dialog.dismiss()
                            findNavController().navigate(R.id.action_chooseOrderFragment_to_homeFragment)
                        }
                        .create()
                        .show()

                }
                .setNegativeButton("არა") { dialog, _ ->
                    dialog.dismiss()
                }
                .create()
                .show()
        }
    }
}