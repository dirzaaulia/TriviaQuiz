package com.dirzaaulia.triviaquiz.ui.home

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.dirzaaulia.triviaquiz.R
import com.dirzaaulia.triviaquiz.data.model.Category
import com.dirzaaulia.triviaquiz.ui.common.CommonLoading
import com.dirzaaulia.triviaquiz.ui.main.MainActivity
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding

@Composable
fun Home(
  viewModel: HomeViewModel,
  navigateToTrivia: (Int, Int, String, String) -> Unit
) {

  val activity = (LocalContext.current as? MainActivity)
  val loading = viewModel.loading.value
  val category by viewModel.category.collectAsState()

  BackHandler {
    activity?.finish()
  }

  CommonLoading(visibility = loading)
  AnimatedVisibility(visible = !loading) {
    Scaffold(
      backgroundColor = MaterialTheme.colors.primarySurface,
      topBar = {
        HomeTopBar()
      }
    ) { innerPadding ->
      val modifier = Modifier
        .navigationBarsPadding()
        .padding(innerPadding)
      HomeContent(modifier, viewModel, category, navigateToTrivia)
    }
  }
}

@Composable
fun HomeContent(
  modifier: Modifier,
  viewModel: HomeViewModel,
  category: List<Category>?,
  navigateToTrivia: (Int, Int, String, String) -> Unit
) {

  //Number of Questions
  var numberOfQuestions by rememberSaveable { mutableStateOf(10) }

  //Category
  var categoryIndex by rememberSaveable { mutableStateOf(0) }
  var categoryQuery by rememberSaveable {
    mutableStateOf(category?.get(0)?.name ?: "Any Category")
  }
  var categoryExpanded by remember { mutableStateOf(false) }
  var categoryFieldSize by remember { mutableStateOf(Size.Zero) }
  val categoryIcon = if (categoryExpanded)
    Icons.Filled.ArrowDropUp //it requires androidx.compose.material:material-icons-extended
  else
    Icons.Filled.ArrowDropDown

  //Difficulty
  val difficulty = listOf("Any Difficulty", "Easy", "Medium", "Hard")
  var difficultyIndex by rememberSaveable { mutableStateOf(0) }
  var difficultyQuery by rememberSaveable { mutableStateOf(difficulty[0]) }
  var difficultyExpanded by remember { mutableStateOf(false) }
  var difficultyFieldSize by remember { mutableStateOf(Size.Zero) }
  val difficultyIcon = if (difficultyExpanded)
    Icons.Filled.ArrowDropUp
  else
    Icons.Filled.ArrowDropDown

  //Type
  val type = listOf("Any Type", "Multiple Choice", "True / False")
  var typeIndex by rememberSaveable { mutableStateOf(0) }
  var typeQuery by rememberSaveable { mutableStateOf(type[0]) }
  var typeExpanded by remember { mutableStateOf(false) }
  var typeFieldSize by remember { mutableStateOf(Size.Zero) }
  val typeIcon = if (typeExpanded)
    Icons.Filled.ArrowDropUp
  else
    Icons.Filled.ArrowDropDown

  Column(
    modifier = modifier
      .padding(vertical = 8.dp, horizontal = 24.dp)
      .fillMaxSize()
  ) {
    Text(
      text = "Welcome to Trivia Quiz!",
      style = MaterialTheme.typography.h6,
      color = MaterialTheme.colors.onSurface
    )
    Text(
      text = "You can customize your own Trivia Quiz setting below or you can randomize the quiz.",
      style = MaterialTheme.typography.subtitle1,
      color = MaterialTheme.colors.onSurface
    )
    OutlinedTextField(
      value = numberOfQuestions.toString(),
      onValueChange = { value ->
        numberOfQuestions = if (value.isEmpty() || value == "0") {
          1
        } else {
          value.filter { it.isDigit() }.toInt()
        }
      },
      modifier = Modifier
        .fillMaxWidth()
        .padding(top = 16.dp),
      textStyle = TextStyle(color = MaterialTheme.colors.onSurface),
      label = {
        Text(
          text = "Number Of Questions",
          color = MaterialTheme.colors.onSurface
        )
      },
      keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
    OutlinedTextField(
      value = categoryQuery,
      onValueChange = { categoryQuery = it },
      modifier = Modifier
        .fillMaxWidth()
        .padding(top = 8.dp)
        .onGloballyPositioned { coordinates ->
          categoryFieldSize = coordinates.size.toSize()
        },
      textStyle = TextStyle(color = MaterialTheme.colors.onSurface),
      label = {
        Text(
          text = "Category",
          color = MaterialTheme.colors.onSurface
        )
      },
      trailingIcon = {
        Icon(
          imageVector = categoryIcon,
          contentDescription = null,
          modifier = Modifier.clickable { categoryExpanded = !categoryExpanded }
        )
      },
      readOnly = true
    )
    DropdownMenu(
      expanded = categoryExpanded,
      onDismissRequest = { categoryExpanded = false },
      modifier = Modifier
        .width(with(LocalDensity.current) { categoryFieldSize.width.toDp() })
    ) {
      category?.forEachIndexed { index, item ->
        DropdownMenuItem(
          onClick = {
            categoryIndex = index + 1
            categoryQuery = item.name.toString()
            categoryExpanded = false
            viewModel.setSelectedCategory(categoryIndex)
          }
        ) {
          Text(text = item.name.toString())
        }
      }
    }
    Box {
      OutlinedTextField(
        value = difficultyQuery,
        onValueChange = { difficultyQuery = it },
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 8.dp)
          .onGloballyPositioned { coordinates ->
            difficultyFieldSize = coordinates.size.toSize()
          },
        textStyle = TextStyle(color = MaterialTheme.colors.onSurface),
        label = {
          Text(
            text = "Difficulty",
            color = MaterialTheme.colors.onSurface
          )
        },
        trailingIcon = {
          Icon(
            imageVector = difficultyIcon,
            contentDescription = null,
            modifier = Modifier.clickable { difficultyExpanded = !difficultyExpanded }
          )
        },
        readOnly = true
      )
      DropdownMenu(
        expanded = difficultyExpanded,
        onDismissRequest = { difficultyExpanded = false },
        modifier = Modifier
          .width(with(LocalDensity.current) { difficultyFieldSize.width.toDp() })
      ) {
        difficulty.forEachIndexed { index, item ->
          DropdownMenuItem(
            onClick = {
              difficultyIndex = index + 1
              difficultyQuery = item
              difficultyExpanded = false
              viewModel.setSelectedDifficulty(difficultyQuery)
            }
          ) {
            Text(text = item)
          }
        }
      }
    }
    Box {
      OutlinedTextField(
        value = typeQuery,
        onValueChange = { typeQuery = it },
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 8.dp)
          .onGloballyPositioned { coordinates ->
            typeFieldSize = coordinates.size.toSize()
          },
        textStyle = TextStyle(color = MaterialTheme.colors.onSurface),
        label = {
          Text(
            text = "Difficulty",
            color = MaterialTheme.colors.onSurface
          )
        },
        trailingIcon = {
          Icon(
            imageVector = typeIcon,
            contentDescription = null,
            modifier = Modifier.clickable { typeExpanded = !typeExpanded }
          )
        },
        readOnly = true
      )
      DropdownMenu(
        expanded = typeExpanded,
        onDismissRequest = { typeExpanded = false },
        modifier = Modifier
          .width(with(LocalDensity.current) { typeFieldSize.width.toDp() })
      ) {
        difficulty.forEachIndexed { index, item ->
          DropdownMenuItem(
            onClick = {
              typeIndex = index + 1
              typeQuery = item
              typeExpanded = false
              viewModel.setSelectedType(typeQuery)
            }
          ) {
            Text(text = item)
          }
        }
      }
    }
    OutlinedButton(
      modifier = Modifier
        .padding(top = 8.dp)
        .fillMaxWidth(),
      onClick = {
        if (categoryIndex == 0) {
          val tempCategory = category?.toMutableList()
          tempCategory?.removeAt(0)
          categoryIndex = tempCategory?.random()?.id!!
        }

        if (difficultyIndex == 0) {
          val tempDifficulty = difficulty.toMutableList()
          tempDifficulty.removeAt(0)
          difficultyQuery = tempDifficulty.random().lowercase()
        }

        if (typeIndex == 0) {
          val tempType = type.toMutableList()
          tempType.removeAt(0)
          typeQuery = tempType.random()

          typeQuery = if (typeQuery.equals("True / False", true)) {
            "boolean"
          } else {
            "multiple"
          }
        }

        navigateToTrivia(numberOfQuestions, categoryIndex, difficultyQuery, typeQuery)
      },
    ) {
      Text(
        style = MaterialTheme.typography.button,
        color = MaterialTheme.colors.onSurface,
        text = "Create Trivia Quiz"
      )
    }
  }
}

@Composable
fun HomeTopBar() {
  TopAppBar(
    backgroundColor = MaterialTheme.colors.primarySurface,
    modifier = Modifier
      .statusBarsPadding()
      .wrapContentHeight()
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.Center
    ) {
      Image(
        painter = painterResource(id = R.drawable.ic_trivia),
        contentDescription = null,
        contentScale = ContentScale.Fit,
        modifier = Modifier
          .padding(0.dp, 16.dp, 0.dp, 16.dp)
          .align(Alignment.CenterVertically)
          .size(100.dp, 0.dp)
          .aspectRatio(1.0f)
      )
    }
  }
}