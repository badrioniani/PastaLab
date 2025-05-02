package com.example.pastaorderapp.features.chooseOrder

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pastaorderapp.R
import com.example.pastaorderapp.databinding.FragmentChooseOrderBinding
import com.example.pastaorderapp.features.ExampleViewModel
import com.example.pastaorderapp.features.adapter.IngredientAdapter
import com.example.pastaorderapp.features.adapter.OrdersAdapter
import com.example.pastaorderapp.features.adapter.PastaAdapter
import com.example.pastaorderapp.features.adapter.SauceAdapter
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

    private var selectedPasta: String? = null
    private var selectedSauce: String? = null

    private val allSauces = listOf(
        Sauce("პესტო", img = R.drawable.pesto_souse, "რიგატონი"),
        Sauce("კაჩიო პეპე", img = R.drawable.kachio_pepe_sousi, "რიგატონი"),
        Sauce("ალ პომიდორი", img = R.drawable.al_pomidoro_sousi, "რიგატონი"),
        Sauce("კარბონარა", img = R.drawable.karbonara_sousi, "რიგატონი"),
        Sauce("ბლუ ჩიზი", img = R.drawable.kachio_pepe_sousi, "რიგატონი"),

        Sauce("პესტო", img = R.drawable.pesto_souse, "პენე"),
        Sauce("კაჩიო პეპე", img = R.drawable.kachio_pepe_sousi, "პენე"),
        Sauce("ალ პომიდორი", img = R.drawable.al_pomidoro_sousi, "პენე"),
        Sauce("კარბონარა", img = R.drawable.karbonara_sousi, "პენე"),
        Sauce("ბლუ ჩიზი", img = R.drawable.kachio_pepe_sousi, "პენე"),
        Sauce("სოკოს პასტა\nტრიუფელით", img = R.drawable.sokos_pasta_sousi, "პენე"),

        Sauce("პესტო", img = R.drawable.pesto_souse, "ტალიატელე"),
        Sauce("ალ პომიდორი", img = R.drawable.al_pomidoro_sousi, "ტალიატელე"),
        Sauce("კარბონარა", img = R.drawable.karbonara_sousi, "ტალიატელე"),
        Sauce("ბლუ ჩიზი", img = R.drawable.kachio_pepe_sousi, "ტალიატელე"),
        Sauce("სოკოს პასტა\nტრიუფელით", img = R.drawable.sokos_pasta_sousi, "ტალიატელე"),
        Sauce("ბოლონეზე", img = R.drawable.boloneza_sousi, "ტალიატელე"),
        Sauce("ზღვის პროდუქტები", img = R.drawable.zghvis_produqtebis_spusi, "ტალიატელე"),

        Sauce("პესტო", img = R.drawable.pesto_souse, "სპაგეტი"),
        Sauce("ალ პომიდორი", img = R.drawable.al_pomidoro_sousi, "სპაგეტი"),
        Sauce("კარბონარა", img = R.drawable.karbonara_sousi, "სპაგეტი"),
        Sauce("ბლუ ჩიზი", img = R.drawable.kachio_pepe_sousi, "სპაგეტი"),
        Sauce("სოკოს პასტა\nტრიუფელით", img = R.drawable.sokos_pasta_sousi, "სპაგეტი"),
        Sauce("ბოლონეზე", img = R.drawable.boloneza_sousi, "სპაგეტი"),
        Sauce("ზღვის პროდუქტები", img = R.drawable.zghvis_produqtebis_spusi, "სპაგეტი"),
    )

    private val allIngredients = listOf(
        Ingredient("ზეთის ხილი", img = R.drawable.zetis_xili, "რიგატონი", "პესტო"),
        Ingredient("კაპერსი", img = R.drawable.kapersi, "რიგატონი", "პესტო"),
        Ingredient("პომიდვრის ჩერი", img = R.drawable.cheri_pomidori, "რიგატონი", "პესტო"),
        Ingredient("პარმაჰემი", img = R.drawable.parmahemi, "რიგატონი", "პესტო"),
        Ingredient("სტრაჩატელა", img = R.drawable.strachatela, "რიგატონი", "პესტო"),
        Ingredient("პარმეზანი", img = R.drawable.parmezani, "რიგატონი", "პესტო"),
        Ingredient("მობრაწული ჩერი", img = R.drawable.mobrawuli_cheri, "რიგატონი", "პესტო"),

        Ingredient("ბაზილიკი", img = R.drawable.bazilika, "რიგატონი", "კაჩიო პეპე"),
        Ingredient("ზეთის ხილი", img = R.drawable.zetis_xili, "რიგატონი", "კაჩიო პეპე"),
        Ingredient("კაპერსი", img = R.drawable.kapersi, "რიგატონი", "კაჩიო პეპე"),

        Ingredient("ბეკონი", img = R.drawable.bekon, "რიგატონი", "ალ პომიდორი"),
        Ingredient("ზეთის ხილი", img = R.drawable.zetis_xili, "რიგატონი", "ალ პომიდორი"),
        Ingredient("კაპერსი", img = R.drawable.kapersi, "რიგატონი", "ალ პომიდორი"),
        Ingredient("სტრაჩატელა", img = R.drawable.strachatela, "რიგატონი", "ალ პომიდორი"),
        Ingredient("პარმეზანი", img = R.drawable.parmezani, "რიგატონი", "ალ პომიდორი"),
        Ingredient("ბლუ ჩიზი", img = R.drawable.bekon, "რიგატონი", "ალ პომიდორი"),

        Ingredient("კიმჩის სოუსი", img = R.drawable.kimchis_sousi, "რიგატონი", "კარბონარა"),
        Ingredient("ბეკონი", img = R.drawable.bekon, "რიგატონი", "კარბონარა"),
        Ingredient("პარმეზანი", img = R.drawable.parmezani, "რიგატონი", "კარბონარა"),
        Ingredient("ბაზილიკი", img = R.drawable.bazilika, "რიგატონი", "კარბონარა"),
        Ingredient("პომიდვრის ჩერი", img = R.drawable.cheri_pomidori, "რიგატონი", "კარბონარა"),
        Ingredient("პეკორინო", img = R.drawable.cheri_pomidori, "რიგატონი", "კარბონარა"),

        Ingredient("ბაზილიკი", img = R.drawable.bazilika, "რიგატონი", "ბლუ ჩიზი"),
        Ingredient("პომიდვრის ჩერი", img = R.drawable.cheri_pomidori, "რიგატონი", "ბლუ ჩიზი"),
        Ingredient("ზეთის ხილი", img = R.drawable.zetis_xili, "რიგატონი", "ბლუ ჩიზი"),
        Ingredient("კაპერსი", img = R.drawable.kapersi, "რიგატონი", "ბლუ ჩიზი"),
        Ingredient("ბეკონი", img = R.drawable.bekon, "რიგატონი", "ბლუ ჩიზი"),

        Ingredient("ზეთის ხილი", img = R.drawable.zetis_xili, "პენე", "პესტო"),
        Ingredient("კაპერსი", img = R.drawable.kapersi, "პენე", "პესტო"),
        Ingredient("პომიდვრის ჩერი", img = R.drawable.cheri_pomidori, "პენე", "პესტო"),
        Ingredient("პარმაჰემი", img = R.drawable.parmahemi, "პენე", "პესტო"),
        Ingredient("სტრაჩატელა", img = R.drawable.strachatela, "პენე", "პესტო"),
        Ingredient("პარმეზანი", img = R.drawable.parmezani, "პენე", "პესტო"),
        Ingredient("მობრაწული ჩერი", img = R.drawable.mobrawuli_cheri, "პენე", "პესტო"),

        Ingredient("ბაზილიკი", img = R.drawable.bazilika, "პენე", "კაჩიო პეპე"),
        Ingredient("ზეთის ხილი", img = R.drawable.zetis_xili, "პენე", "კაჩიო პეპე"),
        Ingredient("კაპერსი", img = R.drawable.kapersi, "პენე", "კაჩიო პეპე"),

        Ingredient("ბეკონი", img = R.drawable.bekon, "პენე", "ალ პომიდორი"),
        Ingredient("ზეთის ხილი", img = R.drawable.zetis_xili, "პენე", "ალ პომიდორი"),
        Ingredient("კაპერსი", img = R.drawable.kapersi, "პენე", "ალ პომიდორი"),
        Ingredient("სტრაჩატელა", img = R.drawable.strachatela, "პენე", "ალ პომიდორი"),
        Ingredient("პარმეზანი", img = R.drawable.parmezani, "პენე", "ალ პომიდორი"),
        Ingredient("ბლუ ჩიზი", img = R.drawable.parmezani, "პენე", "ალ პომიდორი"),

        Ingredient("2X სოკო", img = R.drawable.soko, "პენე", "სოკოს პასტა\nტრიუფელით"),
        Ingredient("ბეკონი", img = R.drawable.bekon, "პენე", "სოკოს პასტა\nტრიუფელით"),
        Ingredient("კაპერსი", img = R.drawable.kapersi, "პენე", "სოკოს პასტა\nტრიუფელით"),
        Ingredient("პარმეზანი", img = R.drawable.parmezani, "პენე", "სოკოს პასტა\nტრიუფელით"),

        Ingredient("ბაზილიკი", img = R.drawable.bazilika, "პენე", "ბლუ ჩიზი"),
        Ingredient("პომიდვრის ჩერი", img = R.drawable.cheri_pomidori, "პენე", "ბლუ ჩიზი"),
        Ingredient("ზეთის ხილი", img = R.drawable.zetis_xili, "პენე", "ბლუ ჩიზი"),
        Ingredient("კაპერსი", img = R.drawable.kapersi, "პენე", "ბლუ ჩიზი"),
        Ingredient("ბეკონი", img = R.drawable.bekon, "პენე", "ბლუ ჩიზი"),

        Ingredient("ზეთის ხილი", img = R.drawable.zetis_xili, "ტალიატელე", "პესტო"),
        Ingredient("კაპერსი", img = R.drawable.kapersi, "ტალიატელე", "პესტო"),
        Ingredient("პომიდვრის ჩერი", img = R.drawable.cheri_pomidori, "ტალიატელე", "პესტო"),
        Ingredient("პარმაჰემი", img = R.drawable.parmahemi, "ტალიატელე", "პესტო"),
        Ingredient("სტრაჩატელა", img = R.drawable.strachatela, "ტალიატელე", "პესტო"),
        Ingredient("პარმეზანი", img = R.drawable.parmezani, "ტალიატელე", "პესტო"),
        Ingredient("მობრაწული ჩერი", img = R.drawable.mobrawuli_cheri, "ტალიატელე", "პესტო"),

        Ingredient("ბეკონი", img = R.drawable.bekon, "ტალიატელე", "ალ პომიდორი"),
        Ingredient("ზეთის ხილი", img = R.drawable.zetis_xili, "ტალიატელე", "ალ პომიდორი"),
        Ingredient("კაპერსი", img = R.drawable.kapersi, "ტალიატელე", "ალ პომიდორი"),
        Ingredient("სტრაჩატელა", img = R.drawable.strachatela, "ტალიატელე", "ალ პომიდორი"),
        Ingredient("პარმეზანი", img = R.drawable.parmezani, "ტალიატელე", "ალ პომიდორი"),
        Ingredient("ბლუ ჩიზი", img = R.drawable.parmezani, "ტალიატელე", "ალ პომიდორი"),

        Ingredient("2X სოკო", img = R.drawable.soko, "ტალიატელე", "სოკოს პასტა\nტრიუფელით"),
        Ingredient("ბეკონი", img = R.drawable.bekon, "ტალიატელე", "სოკოს პასტა\nტრიუფელით"),
        Ingredient("კაპერსი", img = R.drawable.kapersi, "ტალიატელე", "სოკოს პასტა\nტრიუფელით"),
        Ingredient("პარმეზანი", img = R.drawable.parmezani, "ტალიატელე", "სოკოს პასტა\nტრიუფელით"),

        Ingredient("კიმჩის სოუსი", img = R.drawable.kimchis_sousi, "ტალიატელე", "კარბონარა"),
        Ingredient("ბეკონი", img = R.drawable.bekon, "ტალიატელე", "კარბონარა"),
        Ingredient("პარმეზანი", img = R.drawable.parmezani, "ტალიატელე", "კარბონარა"),
        Ingredient("ბაზილიკი", img = R.drawable.bazilika, "ტალიატელე", "კარბონარა"),
        Ingredient("პომიდვრის ჩერი", img = R.drawable.cheri_pomidori, "ტალიატელე", "კარბონარა"),
        Ingredient("პეკორინო", img = R.drawable.cheri_pomidori, "ტალიატელე", "კარბონარა"),

        Ingredient("ბაზილიკი", img = R.drawable.bazilika, "ტალიატელე", "ბლუ ჩიზი"),
        Ingredient("პომიდვრის ჩერი", img = R.drawable.cheri_pomidori, "ტალიატელე", "ბლუ ჩიზი"),
        Ingredient("ზეთის ხილი", img = R.drawable.zetis_xili, "ტალიატელე", "ბლუ ჩიზი"),
        Ingredient("კაპერსი", img = R.drawable.kapersi, "ტალიატელე", "ბლუ ჩიზი"),
        Ingredient("ბეკონი", img = R.drawable.bekon, "ტალიატელე", "ბლუ ჩიზი"),

        Ingredient("ნაღები", img = R.drawable.bekon, "ტალიატელე", "ბოლონეზე"),
        Ingredient("პომიდვრის ჩერი", img = R.drawable.cheri_pomidori, "ტალიატელე", "ბოლონეზე"),
        Ingredient("კაპერსი", img = R.drawable.kapersi, "ტალიატელე", "ბოლონეზე"),
        Ingredient("ზეთის ხილი", img = R.drawable.zetis_xili, "ტალიატელე", "ბოლონეზე"),
        Ingredient("ჰალაპენიო", img = R.drawable.halapenio, "ტალიატელე", "ბოლონეზე"),
        Ingredient("პარმეზანი", img = R.drawable.parmezani, "ტალიატელე", "ბოლონეზე"),

        Ingredient("2X ზღვის პროდუქტი", img = R.drawable.zghvis_produqtebis_spusi, "ტალიატელე", "ზღვის პროდუქტები"),
        Ingredient("პომიდვრის ჩერი", img = R.drawable.cheri_pomidori, "ტალიატელე", "ზღვის პროდუქტები"),
        Ingredient("ზეთის ხილი", img = R.drawable.zetis_xili, "ტალიატელე", "ზღვის პროდუქტები"),
        Ingredient("კაპერსი", img = R.drawable.kapersi, "ტალიატელე", "ზღვის პროდუქტები"),

        Ingredient("ზეთის ხილი", img = R.drawable.zetis_xili, "სპაგეტი", "პესტო"),
        Ingredient("კაპერსი", img = R.drawable.kapersi, "სპაგეტი", "პესტო"),
        Ingredient("პომიდვრის ჩერი", img = R.drawable.cheri_pomidori, "სპაგეტი", "პესტო"),
        Ingredient("პარმაჰემი", img = R.drawable.parmahemi, "სპაგეტი", "პესტო"),
        Ingredient("სტრაჩატელა", img = R.drawable.strachatela, "სპაგეტი", "პესტო"),
        Ingredient("პარმეზანი", img = R.drawable.parmezani, "სპაგეტი", "პესტო"),
        Ingredient("მობრაწული ჩერი", img = R.drawable.mobrawuli_cheri, "სპაგეტი", "პესტო"),

        Ingredient("ბეკონი", img = R.drawable.bekon, "სპაგეტი", "ალ პომიდორი"),
        Ingredient("ზეთის ხილი", img = R.drawable.zetis_xili, "სპაგეტი", "ალ პომიდორი"),
        Ingredient("კაპერსი", img = R.drawable.kapersi, "სპაგეტი", "ალ პომიდორი"),
        Ingredient("სტრაჩატელა", img = R.drawable.strachatela, "სპაგეტი", "ალ პომიდორი"),
        Ingredient("პარმეზანი", img = R.drawable.parmezani, "სპაგეტი", "ალ პომიდორი"),
        Ingredient("ბლუ ჩიზი", img = R.drawable.parmezani, "სპაგეტი", "ალ პომიდორი"),

        Ingredient("2X სოკო", img = R.drawable.soko, "სპაგეტი", "სოკოს პასტა\nტრიუფელით"),
        Ingredient("ბეკონი", img = R.drawable.bekon, "სპაგეტი", "სოკოს პასტა\nტრიუფელით"),
        Ingredient("კაპერსი", img = R.drawable.kapersi, "სპაგეტი", "სოკოს პასტა\nტრიუფელით"),
        Ingredient("პარმეზანი", img = R.drawable.parmezani, "სპაგეტი", "სოკოს პასტა\nტრიუფელით"),

        Ingredient("კიმჩის სოუსი", img = R.drawable.kimchis_sousi, "სპაგეტი", "კარბონარა"),
        Ingredient("ბეკონი", img = R.drawable.bekon, "სპაგეტი", "კარბონარა"),
        Ingredient("პარმეზანი", img = R.drawable.parmezani, "სპაგეტი", "კარბონარა"),
        Ingredient("ბაზილიკი", img = R.drawable.bazilika, "სპაგეტი", "კარბონარა"),
        Ingredient("პომიდვრის ჩერი", img = R.drawable.cheri_pomidori, "სპაგეტი", "კარბონარა"),
        Ingredient("ბეკონირო", img = R.drawable.bekon, "სპაგეტი", "კარბონარა"),

        Ingredient("ბაზილიკი", img = R.drawable.bazilika, "სპაგეტი", "ბლუ ჩიზი"),
        Ingredient("პომიდვრის ჩერი", img = R.drawable.cheri_pomidori, "სპაგეტი", "ბლუ ჩიზი"),
        Ingredient("ზეთის ხილი", img = R.drawable.zetis_xili, "სპაგეტი", "ბლუ ჩიზი"),
        Ingredient("კაპერსი", img = R.drawable.kapersi, "სპაგეტი", "ბლუ ჩიზი"),
        Ingredient("ბეკონი", img = R.drawable.bekon, "სპაგეტი", "ბლუ ჩიზი"),

        Ingredient("ნაღები", img = R.drawable.bekon, "სპაგეტი", "ბოლონეზე"),
        Ingredient("პომიდვრის ჩერი", img = R.drawable.cheri_pomidori, "სპაგეტი", "ბოლონეზე"),
        Ingredient("კაპერსი", img = R.drawable.kapersi, "სპაგეტი", "ბოლონეზე"),
        Ingredient("ზეთის ხილი", img = R.drawable.zetis_xili, "სპაგეტი", "ბოლონეზე"),
        Ingredient("ჰალაპენიო", img = R.drawable.halapenio, "სპაგეტი", "ბოლონეზე"),
        Ingredient("პარმეზანი", img = R.drawable.parmezani, "სპაგეტი", "ბოლონეზე"),

        Ingredient("2X ზღვის პროდუქტი", img = R.drawable.zghvis_produqtebis_spusi, "სპაგეტი", "ზღვის პროდუქტები"),
        Ingredient("პომიდვრის ჩერი", img = R.drawable.cheri_pomidori, "სპაგეტი", "ზღვის პროდუქტები"),
        Ingredient("ზეთის ხილი", img = R.drawable.zetis_xili, "სპაგეტი", "ზღვის პროდუქტები"),
        Ingredient("კაპერსი", img = R.drawable.kapersi, "სპაგეტი", "ზღვის პროდუქტები"),
    )

    private val viewModel: ExampleViewModel by viewModels()
    private val binding by lazy {
        FragmentChooseOrderBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        val orderList = listOf(
            OrderModel(
                id = 100,
                isTrend = true,
                name = "შექმენი შენი პასტა",
                img = R.drawable.create_your_own,
                orderId = "",
                price = "25.34",
                isFire = false
            ), OrderModel(
                id = 1,
                isTrend = false,
                orderId = "c61419c7-686a-473c-a162-ac85ee7f6f9d",
                name = "ბოლონეზა",
                img = R.drawable.boloneza,
                price = "25.34",
                isFire = false
            ), OrderModel(
                id = 1,
                isTrend = false,
                orderId = "ca6bcca4-9ee8-444c-8646-880ac13e3edf",
                name = "ნიოკი ქათმით და კიმჩით",
                img = R.drawable.nioki,
                price = "25.34",
                isFire = true
            ), OrderModel(
                id = 1,
                isTrend = false,
                orderId = "9a681600-b84f-4bbc-8093-7d64e53f2720",
                name = "რიგატონინი პესტო",
                img = R.drawable.rigatoni_pesto,
                price = 25.34.toString(),
                isFire = true
            ), OrderModel(
                id = 1,
                isTrend = false,
                orderId = "0b2a467b-25dd-4002-a6a2-14e66c5d1b88",
                name = "კარბონარა",
                img = R.drawable.karbonara,
                price = 25.34.toString(),
                isFire = false
            ), OrderModel(
                id = 1,
                isTrend = false,
                orderId = "b36a7f74-c08c-492a-8e59-db2eb960085a",
                name = "ზღვის პროდუქტების პასტა",
                img = R.drawable.zgvis_produqtebis,
                price = 25.34.toString(),
                isFire = false
            ), OrderModel(
                id = 1,
                isTrend = false,
                orderId = "b36a7f74-c08c-492a-8e59-db2eb960085a",
                name = "ალ პომიდორო",
                img = R.drawable.al_pomidoro,
                price = 25.34.toString(),
                isFire = false
            ), OrderModel(
                id = 1,
                isTrend = false,
                orderId = "08c41f98-7e16-49b3-b946-93c86127a096",
                name = "სოკოს პასტა ტრიუფელით",
                img = R.drawable.sokos_pasta,
                price = 25.34.toString(),
                isFire = false
            ), OrderModel(
                id = 1,
                isTrend = false,
                orderId = "a5630ec6-7d8e-4ca3-8ed2-0126f8799fe5",
                name = "ბლუ ჩიზის პასტა",
                img = R.drawable.lurji_yvelis_pasta,
                price = 25.34.toString(),
                isFire = false
            ), OrderModel(
                id = 1,
                isTrend = false,
                orderId = "759fb5b4-3014-4e74-a4ee-4528d21a851a",
                name = "ნიოკის პესტო სტრაჩატელი",
                img = R.drawable.niokis_pesto,
                price = 25.34.toString(),
                isFire = false
            ), OrderModel(
                id = 1,
                isTrend = false,
                orderId = "9e42ed40-be6e-4177-8601-ed5bcef7f909",
                name = "კაჩიო პეპე",
                img = R.drawable.kachio_pepe,
                price = 25.34.toString(),
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
                price = 25.34.toString(),
                isFire = false
            ), OrderModel(
                id = 1,
                isTrend = false,
                orderId = "dd4b12dc-8ba0-4ce8-8f78-0f3d16f06a32",
                name = "სალათი - ორაგულის",
                img = R.drawable.oragulis_salati,
                price = 25.34.toString(),
                isFire = false
            ), OrderModel(
                id = 1,
                isTrend = false,
                orderId = "2f6b4f42-2d9b-4fbb-b63b-13859f78e8e0",
                name = "სალათი - ფრეში",
                img = R.drawable.fresh_salati,
                price = 25.34.toString(),
                isFire = false
            )
        )
        val drinksList = listOf(
            OrderModel(
                id = 1,
                isTrend = false,
                orderId = "",
                name = "კოკა-კოლა",
                img = R.drawable.coca_cola,
                price = 25.34.toString(),
                isFire = false
            ), OrderModel(
                id = 1,
                isTrend = false,
                orderId = "",
                name = "ცივი ჩაი ატმის",
                img = R.drawable.civi_atmis,
                price = 25.34.toString(),
                isFire = false
            ), OrderModel(
                id = 1,
                isTrend = false,
                orderId = "",
                name = "ცივი ჩაი მანგო",
                img = R.drawable.civi_mango,
                price = 25.34.toString(),
                isFire = false
            ),
            OrderModel(
                id = 1,
                isTrend = false,
                orderId = "",
                name = "ცივი ჩაი კენკრის",
                img = R.drawable.civi_kenkra,
                price = 25.34.toString(),
                isFire = false
            ), OrderModel(
                id = 1,
                isTrend = false,
                orderId = "",
                name = "კაპი ფორთხოვლი",
                img = R.drawable.kapi_fortoxali,
                price = 25.34.toString(),
                isFire = false
            ), OrderModel(
                id = 1,
                isTrend = false,
                orderId = "",
                name = "კაბი ალუბლის",
                img = R.drawable.capi_alubali,
                price = 25.34.toString(),
                isFire = false
            )
        )
        val selectedDishes = mutableListOf<OrderModel>()
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

//                viewModel.examples.observe(viewLifecycleOwner) { examples ->
//                    binding.title1.text = examples.token
//                }
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


        return binding.root
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
                PastaType("რიგატონი", R.drawable.rigato),
                PastaType("პენე", R.drawable.pene),
                PastaType("ტალიატელე", R.drawable.taliatele),
                PastaType("სპაგეტი", R.drawable.spageti),
            ),
            singleSelect = true,
            isSelected = { it.isSelected },
            setSelected = { item, selected -> item.isSelected = selected },
            onSelectionChanged = {},
            onClick = { pasta ->
                onPastaSelected(pasta.name)
            })

        sauceAdapter = SauceAdapter(
            emptyList(),
            singleSelect = true,
            isSelected = { it.isSelected },
            setSelected = { item, selected -> item.isSelected = selected },
            onSelectionChanged = {},
            onClick = { sauce ->
                onSauceSelected(sauce.name)
            })

        ingredientAdapter = IngredientAdapter(
            emptyList(),
            singleSelect = false,
            isSelected = { it.isSelected },
            setSelected = { item, selected -> item.isSelected = selected },
            onSelectionChanged = {},
            onClick = {})

        rvPasta.adapter = pastaAdapter
        rvSauces.adapter = sauceAdapter
        rvIngredients.adapter = ingredientAdapter

        rvSauces.visibility = View.GONE
        rvIngredients.visibility = View.GONE
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
}