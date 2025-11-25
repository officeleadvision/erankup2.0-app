package com.erankup.customersatisfaction.ui.vote;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.widget.Toast;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.material3.ButtonDefaults;
import androidx.compose.material3.CheckboxDefaults;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.foundation.text.KeyboardOptions;
import androidx.compose.ui.text.input.KeyboardType;
import androidx.compose.ui.text.input.PasswordVisualTransformation;
import androidx.compose.ui.text.style.TextAlign;
import androidx.compose.ui.layout.ContentScale;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import com.erankup.customersatisfaction.MainActivity;
import com.erankup.customersatisfaction.R;
import com.erankup.customersatisfaction.domain.model.Question;
import com.erankup.customersatisfaction.domain.model.QuestionAnswer;
import com.erankup.customersatisfaction.domain.model.VoteType;
import androidx.annotation.DrawableRes;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000\u008a\u0001\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u00ce\u0004\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\u00012\u0006\u0010\b\u001a\u00020\u00012\u0006\u0010\t\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\u00012\u0006\u0010\f\u001a\u00020\u00012\u0006\u0010\r\u001a\u00020\u00012\u0006\u0010\u000e\u001a\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u00012\u00e3\u0001\u0010\u0010\u001a\u00de\u0001\u0012\u0013\u0012\u00110\u0001\u00a2\u0006\f\b\u0012\u0012\b\b\u0013\u0012\u0004\b\b(\u0014\u0012\u0013\u0012\u00110\u0001\u00a2\u0006\f\b\u0012\u0012\b\b\u0013\u0012\u0004\b\b(\u0015\u0012\u0013\u0012\u00110\u0001\u00a2\u0006\f\b\u0012\u0012\b\b\u0013\u0012\u0004\b\b(\u0016\u0012\u0015\u0012\u0013\u0018\u00010\u0001\u00a2\u0006\f\b\u0012\u0012\b\b\u0013\u0012\u0004\b\b(\u0017\u0012\u0015\u0012\u0013\u0018\u00010\u0001\u00a2\u0006\f\b\u0012\u0012\b\b\u0013\u0012\u0004\b\b(\u0018\u0012\u0013\u0012\u00110\u0001\u00a2\u0006\f\b\u0012\u0012\b\b\u0013\u0012\u0004\b\b(\u0019\u0012\u0013\u0012\u00110\u0001\u00a2\u0006\f\b\u0012\u0012\b\b\u0013\u0012\u0004\b\b(\u001a\u0012\u0013\u0012\u00110\u0001\u00a2\u0006\f\b\u0012\u0012\b\b\u0013\u0012\u0004\b\b(\u001b\u0012\u0013\u0012\u00110\u0001\u00a2\u0006\f\b\u0012\u0012\b\b\u0013\u0012\u0004\b\b(\u001c\u0012\u0013\u0012\u00110\u0001\u00a2\u0006\f\b\u0012\u0012\b\b\u0013\u0012\u0004\b\b(\u001d\u0012\u0004\u0012\u00020\u00050\u00112\u00e3\u0001\u0010\u001e\u001a\u00de\u0001\u0012\u0013\u0012\u00110\u0001\u00a2\u0006\f\b\u0012\u0012\b\b\u0013\u0012\u0004\b\b(\u0014\u0012\u0013\u0012\u00110\u0001\u00a2\u0006\f\b\u0012\u0012\b\b\u0013\u0012\u0004\b\b(\u0015\u0012\u0013\u0012\u00110\u0001\u00a2\u0006\f\b\u0012\u0012\b\b\u0013\u0012\u0004\b\b(\u0016\u0012\u0015\u0012\u0013\u0018\u00010\u0001\u00a2\u0006\f\b\u0012\u0012\b\b\u0013\u0012\u0004\b\b(\u0017\u0012\u0015\u0012\u0013\u0018\u00010\u0001\u00a2\u0006\f\b\u0012\u0012\b\b\u0013\u0012\u0004\b\b(\u0018\u0012\u0013\u0012\u00110\u0001\u00a2\u0006\f\b\u0012\u0012\b\b\u0013\u0012\u0004\b\b(\u0019\u0012\u0013\u0012\u00110\u0001\u00a2\u0006\f\b\u0012\u0012\b\b\u0013\u0012\u0004\b\b(\u001a\u0012\u0013\u0012\u00110\u0001\u00a2\u0006\f\b\u0012\u0012\b\b\u0013\u0012\u0004\b\b(\u001b\u0012\u0013\u0012\u00110\u0001\u00a2\u0006\f\b\u0012\u0012\b\b\u0013\u0012\u0004\b\b(\u001c\u0012\u0013\u0012\u00110\u0001\u00a2\u0006\f\b\u0012\u0012\b\b\u0013\u0012\u0004\b\b(\u001d\u0012\u0004\u0012\u00020\u00050\u00112\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00050 2\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00050 2\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00050 H\u0003\u001aH\u0010#\u001a\u00020\u00052\u0006\u0010$\u001a\u00020\u00012\u0006\u0010%\u001a\u00020&2\u0012\u0010\'\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00050(2\f\u0010)\u001a\b\u0012\u0004\u0012\u00020\u00050 2\f\u0010*\u001a\b\u0012\u0004\u0012\u00020\u00050 H\u0003\u001a&\u0010+\u001a\u00020\u00052\f\u0010,\u001a\b\u0012\u0004\u0012\u00020.0-2\u0006\u0010/\u001a\u0002002\u0006\u00101\u001a\u000202H\u0003\u001a4\u00103\u001a\u00020\u00052\u0006\u00104\u001a\u00020\u00012\u0006\u00105\u001a\u00020\u00012\u0006\u0010%\u001a\u00020&2\u0012\u00106\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00050(H\u0003\u001a\u00da\u0002\u00107\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\u00012\u0006\u0010\b\u001a\u00020\u00012\u0006\u0010\t\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\u00012\u0006\u0010\f\u001a\u00020\u00012\u0006\u0010\r\u001a\u00020\u00012\u0006\u0010\u000e\u001a\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u00012\u00e3\u0001\u0010\u0010\u001a\u00de\u0001\u0012\u0013\u0012\u00110\u0001\u00a2\u0006\f\b\u0012\u0012\b\b\u0013\u0012\u0004\b\b(\u0014\u0012\u0013\u0012\u00110\u0001\u00a2\u0006\f\b\u0012\u0012\b\b\u0013\u0012\u0004\b\b(\u0015\u0012\u0013\u0012\u00110\u0001\u00a2\u0006\f\b\u0012\u0012\b\b\u0013\u0012\u0004\b\b(\u0016\u0012\u0015\u0012\u0013\u0018\u00010\u0001\u00a2\u0006\f\b\u0012\u0012\b\b\u0013\u0012\u0004\b\b(\u0017\u0012\u0015\u0012\u0013\u0018\u00010\u0001\u00a2\u0006\f\b\u0012\u0012\b\b\u0013\u0012\u0004\b\b(\u0018\u0012\u0013\u0012\u00110\u0001\u00a2\u0006\f\b\u0012\u0012\b\b\u0013\u0012\u0004\b\b(\u0019\u0012\u0013\u0012\u00110\u0001\u00a2\u0006\f\b\u0012\u0012\b\b\u0013\u0012\u0004\b\b(\u001a\u0012\u0013\u0012\u00110\u0001\u00a2\u0006\f\b\u0012\u0012\b\b\u0013\u0012\u0004\b\b(\u001b\u0012\u0013\u0012\u00110\u0001\u00a2\u0006\f\b\u0012\u0012\b\b\u0013\u0012\u0004\b\b(\u001c\u0012\u0013\u0012\u00110\u0001\u00a2\u0006\f\b\u0012\u0012\b\b\u0013\u0012\u0004\b\b(\u001d\u0012\u0004\u0012\u00020\u00050\u00112\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00050 2\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00050 H\u0003\u001a6\u00108\u001a\u00020\u00052\u0006\u00109\u001a\u00020:2\u0012\u0010;\u001a\u000e\u0012\u0004\u0012\u00020:\u0012\u0004\u0012\u00020\u00050(2\u0006\u00101\u001a\u0002022\b\b\u0002\u0010<\u001a\u00020=H\u0003\u001a\u0010\u0010>\u001a\u00020\u00052\u0006\u0010?\u001a\u00020\u0001H\u0003\u001a.\u0010@\u001a\u00020\u00052\u0006\u0010A\u001a\u00020&2\u0006\u0010B\u001a\u00020&2\f\u0010C\u001a\b\u0012\u0004\u0012\u00020\u00050 2\u0006\u00101\u001a\u000202H\u0003\u001a\u0084\u0001\u0010D\u001a\u00020\u00052\u0006\u0010E\u001a\u00020F2\u0012\u0010G\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00050(2\u0012\u0010H\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00050(2\u0012\u0010I\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00050(2\u0012\u0010J\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00050(2\u0012\u0010K\u001a\u000e\u0012\u0004\u0012\u00020&\u0012\u0004\u0012\u00020\u00050(2\u0006\u0010L\u001a\u00020&2\u0006\u00101\u001a\u000202H\u0003\u001a\u008a\u0001\u0010M\u001a\u00020\u00052\u0006\u0010E\u001a\u00020F2\u0012\u0010G\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00050(2\u0012\u0010H\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00050(2\u0012\u0010I\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00050(2\u0012\u0010J\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00050(2\u0012\u0010K\u001a\u000e\u0012\u0004\u0012\u00020&\u0012\u0004\u0012\u00020\u00050(2\f\u0010C\u001a\b\u0012\u0004\u0012\u00020\u00050 2\u0006\u00101\u001a\u000202H\u0003\u001a,\u0010N\u001a\u00020\u00052\b\u0010O\u001a\u0004\u0018\u00010\u00012\u0006\u0010P\u001a\u00020\u00012\u0006\u00101\u001a\u0002022\b\b\u0002\u0010<\u001a\u00020=H\u0003\u001a,\u0010Q\u001a\u00020\u00052\u0006\u0010O\u001a\u00020\u00012\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00050 2\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00050 H\u0003\u001a>\u0010R\u001a\u00020\u00052\b\u0010S\u001a\u0004\u0018\u00010T2\u0006\u0010U\u001a\u0002002\u0006\u0010/\u001a\u0002002\u0012\u0010;\u001a\u000e\u0012\u0004\u0012\u00020:\u0012\u0004\u0012\u00020\u00050(2\u0006\u00101\u001a\u000202H\u0003\u001a\u001e\u0010V\u001a\u00020\u00052\u0006\u0010W\u001a\u00020\u00012\f\u0010X\u001a\b\u0012\u0004\u0012\u00020\u00050 H\u0003\u001a\b\u0010Y\u001a\u00020\u0005H\u0003\u001a\u0018\u0010Z\u001a\u00020\u00052\u0006\u0010W\u001a\u00020\u00012\u0006\u00101\u001a\u000202H\u0003\u001a\u00b4\u0001\u0010[\u001a\u00020\u00052\u0006\u0010E\u001a\u00020F2\u0012\u0010;\u001a\u000e\u0012\u0004\u0012\u00020:\u0012\u0004\u0012\u00020\u00050(2\f\u0010C\u001a\b\u0012\u0004\u0012\u00020\u00050 2\f\u0010\\\u001a\b\u0012\u0004\u0012\u00020\u00050 2\u0012\u0010G\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00050(2\u0012\u0010H\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00050(2\u0012\u0010I\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00050(2\u0012\u0010J\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00050(2\u0012\u0010K\u001a\u000e\u0012\u0004\u0012\u00020&\u0012\u0004\u0012\u00020\u00050(2\u0006\u0010]\u001a\u00020&2\u0006\u0010^\u001a\u00020\u0001H\u0003\u001a\"\u0010_\u001a\u00020\u00052\u0006\u0010`\u001a\u00020\u00012\b\u0010O\u001a\u0004\u0018\u00010\u00012\u0006\u00101\u001a\u000202H\u0003\u001a\u0012\u0010a\u001a\u00020\u00052\b\b\u0002\u0010b\u001a\u00020cH\u0007\u001a\u00b6\u0003\u0010d\u001a\u00020\u00052\u0006\u0010E\u001a\u00020F2\u0012\u0010;\u001a\u000e\u0012\u0004\u0012\u00020:\u0012\u0004\u0012\u00020\u00050(2\f\u0010C\u001a\b\u0012\u0004\u0012\u00020\u00050 2\f\u0010\\\u001a\b\u0012\u0004\u0012\u00020\u00050 2\u00e3\u0001\u0010e\u001a\u00de\u0001\u0012\u0013\u0012\u00110\u0001\u00a2\u0006\f\b\u0012\u0012\b\b\u0013\u0012\u0004\b\b(\u0014\u0012\u0013\u0012\u00110\u0001\u00a2\u0006\f\b\u0012\u0012\b\b\u0013\u0012\u0004\b\b(\u0015\u0012\u0013\u0012\u00110\u0001\u00a2\u0006\f\b\u0012\u0012\b\b\u0013\u0012\u0004\b\b(\u0016\u0012\u0015\u0012\u0013\u0018\u00010\u0001\u00a2\u0006\f\b\u0012\u0012\b\b\u0013\u0012\u0004\b\b(\u0017\u0012\u0015\u0012\u0013\u0018\u00010\u0001\u00a2\u0006\f\b\u0012\u0012\b\b\u0013\u0012\u0004\b\b(\u0018\u0012\u0013\u0012\u00110\u0001\u00a2\u0006\f\b\u0012\u0012\b\b\u0013\u0012\u0004\b\b(\u0019\u0012\u0013\u0012\u00110\u0001\u00a2\u0006\f\b\u0012\u0012\b\b\u0013\u0012\u0004\b\b(\u001a\u0012\u0013\u0012\u00110\u0001\u00a2\u0006\f\b\u0012\u0012\b\b\u0013\u0012\u0004\b\b(\u001b\u0012\u0013\u0012\u00110\u0001\u00a2\u0006\f\b\u0012\u0012\b\b\u0013\u0012\u0004\b\b(\u001c\u0012\u0013\u0012\u00110\u0001\u00a2\u0006\f\b\u0012\u0012\b\b\u0013\u0012\u0004\b\b(\u001d\u0012\u0004\u0012\u00020\u00050\u00112\u0012\u0010G\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00050(2\u0012\u0010H\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00050(2\u0012\u0010I\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00050(2\u0012\u0010J\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00050(2\u0012\u0010K\u001a\u000e\u0012\u0004\u0012\u00020&\u0012\u0004\u0012\u00020\u00050(2\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00050 2\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00050 2\u0006\u0010]\u001a\u00020&2\u0006\u0010^\u001a\u00020\u0001H\u0003\u001a\u0012\u0010f\u001a\u0004\u0018\u00010\u00012\u0006\u00104\u001a\u00020\u0001H\u0002\u001a\u0012\u0010g\u001a\u0004\u0018\u00010h2\u0006\u00104\u001a\u00020\u0001H\u0002\u001a\f\u0010i\u001a\u000200*\u00020:H\u0003\u001a\u000f\u0010j\u001a\u0004\u0018\u00010k*\u00020lH\u0082\u0010\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006m"}, d2 = {"ADMIN_PASSWORD", "", "HexColorRegex", "Lkotlin/text/Regex;", "AdminPanelDialog", "", "initialToken", "initialOwner", "initialBaseUrl", "initialHeaderTitle", "initialHeaderLogoPath", "initialHeaderBackgroundColor", "initialHeaderTextColor", "initialBodyBackgroundColor", "initialBodyTextColor", "initialAccentColor", "onSave", "Lkotlin/Function10;", "Lkotlin/ParameterName;", "name", "token", "owner", "baseUrl", "headerTitle", "headerLogoPath", "headerBackgroundColor", "headerTextColor", "bodyBackgroundColor", "bodyTextColor", "accentColor", "onExit", "onDismiss", "Lkotlin/Function0;", "onPickLogo", "onRemoveLogo", "AdminPasswordDialog", "password", "isError", "", "onPasswordChange", "Lkotlin/Function1;", "onConfirm", "onCancel", "AnsweredQuestionsSummary", "answers", "", "Lcom/erankup/customersatisfaction/domain/model/QuestionAnswer;", "totalQuestions", "", "colors", "Lcom/erankup/customersatisfaction/ui/vote/VoteThemeColors;", "ColorInputField", "value", "label", "onValueChange", "DeviceSetupScreen", "EmojiVoteButton", "voteType", "Lcom/erankup/customersatisfaction/domain/model/VoteType;", "onVoteSelected", "modifier", "Landroidx/compose/ui/Modifier;", "ErrorHint", "text", "FeedbackActions", "isSubmitEnabled", "isLoading", "onSubmitSession", "FeedbackForm", "state", "Lcom/erankup/customersatisfaction/ui/vote/VoteUiState;", "onNameChange", "onPhoneChange", "onEmailChange", "onCommentChange", "onConsentChange", "showConsentWarning", "FeedbackStage", "LogoBadge", "logoPath", "titleFallback", "LogoPreviewSection", "QuestionStep", "question", "Lcom/erankup/customersatisfaction/domain/model/Question;", "questionNumber", "QuestionsError", "message", "onRetry", "QuestionsLoading", "ThankYouScreen", "VoteContent", "onRetryQuestions", "showThankYouScreen", "thankYouMessage", "VoteHeader", "title", "VoteRoute", "viewModel", "Lcom/erankup/customersatisfaction/ui/vote/VoteViewModel;", "VoteScreen", "onSaveDeviceConfig", "normalizeColorInput", "parsedColorOrNull", "Landroidx/compose/ui/graphics/Color;", "drawableRes", "findActivity", "Landroid/app/Activity;", "Landroid/content/Context;", "app_debug"})
public final class VoteScreenKt {
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String ADMIN_PASSWORD = "LeadVision";
    @org.jetbrains.annotations.NotNull()
    private static final kotlin.text.Regex HexColorRegex = null;
    
    @androidx.compose.runtime.Composable()
    public static final void VoteRoute(@org.jetbrains.annotations.NotNull()
    com.erankup.customersatisfaction.ui.vote.VoteViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void VoteHeader(java.lang.String title, java.lang.String logoPath, com.erankup.customersatisfaction.ui.vote.VoteThemeColors colors) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void LogoBadge(java.lang.String logoPath, java.lang.String titleFallback, com.erankup.customersatisfaction.ui.vote.VoteThemeColors colors, androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void QuestionsLoading() {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void QuestionsError(java.lang.String message, kotlin.jvm.functions.Function0<kotlin.Unit> onRetry) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void QuestionStep(com.erankup.customersatisfaction.domain.model.Question question, int questionNumber, int totalQuestions, kotlin.jvm.functions.Function1<? super com.erankup.customersatisfaction.domain.model.VoteType, kotlin.Unit> onVoteSelected, com.erankup.customersatisfaction.ui.vote.VoteThemeColors colors) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void FeedbackStage(com.erankup.customersatisfaction.ui.vote.VoteUiState state, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onNameChange, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onPhoneChange, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onEmailChange, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onCommentChange, kotlin.jvm.functions.Function1<? super java.lang.Boolean, kotlin.Unit> onConsentChange, kotlin.jvm.functions.Function0<kotlin.Unit> onSubmitSession, com.erankup.customersatisfaction.ui.vote.VoteThemeColors colors) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void AnsweredQuestionsSummary(java.util.List<com.erankup.customersatisfaction.domain.model.QuestionAnswer> answers, int totalQuestions, com.erankup.customersatisfaction.ui.vote.VoteThemeColors colors) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void FeedbackActions(boolean isSubmitEnabled, boolean isLoading, kotlin.jvm.functions.Function0<kotlin.Unit> onSubmitSession, com.erankup.customersatisfaction.ui.vote.VoteThemeColors colors) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void VoteScreen(com.erankup.customersatisfaction.ui.vote.VoteUiState state, kotlin.jvm.functions.Function1<? super com.erankup.customersatisfaction.domain.model.VoteType, kotlin.Unit> onVoteSelected, kotlin.jvm.functions.Function0<kotlin.Unit> onSubmitSession, kotlin.jvm.functions.Function0<kotlin.Unit> onRetryQuestions, kotlin.jvm.functions.Function10<? super java.lang.String, ? super java.lang.String, ? super java.lang.String, ? super java.lang.String, ? super java.lang.String, ? super java.lang.String, ? super java.lang.String, ? super java.lang.String, ? super java.lang.String, ? super java.lang.String, kotlin.Unit> onSaveDeviceConfig, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onNameChange, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onPhoneChange, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onEmailChange, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onCommentChange, kotlin.jvm.functions.Function1<? super java.lang.Boolean, kotlin.Unit> onConsentChange, kotlin.jvm.functions.Function0<kotlin.Unit> onPickLogo, kotlin.jvm.functions.Function0<kotlin.Unit> onRemoveLogo, boolean showThankYouScreen, java.lang.String thankYouMessage) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void DeviceSetupScreen(java.lang.String initialToken, java.lang.String initialOwner, java.lang.String initialBaseUrl, java.lang.String initialHeaderTitle, java.lang.String initialHeaderLogoPath, java.lang.String initialHeaderBackgroundColor, java.lang.String initialHeaderTextColor, java.lang.String initialBodyBackgroundColor, java.lang.String initialBodyTextColor, java.lang.String initialAccentColor, kotlin.jvm.functions.Function10<? super java.lang.String, ? super java.lang.String, ? super java.lang.String, ? super java.lang.String, ? super java.lang.String, ? super java.lang.String, ? super java.lang.String, ? super java.lang.String, ? super java.lang.String, ? super java.lang.String, kotlin.Unit> onSave, kotlin.jvm.functions.Function0<kotlin.Unit> onPickLogo, kotlin.jvm.functions.Function0<kotlin.Unit> onRemoveLogo) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void VoteContent(com.erankup.customersatisfaction.ui.vote.VoteUiState state, kotlin.jvm.functions.Function1<? super com.erankup.customersatisfaction.domain.model.VoteType, kotlin.Unit> onVoteSelected, kotlin.jvm.functions.Function0<kotlin.Unit> onSubmitSession, kotlin.jvm.functions.Function0<kotlin.Unit> onRetryQuestions, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onNameChange, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onPhoneChange, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onEmailChange, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onCommentChange, kotlin.jvm.functions.Function1<? super java.lang.Boolean, kotlin.Unit> onConsentChange, boolean showThankYouScreen, java.lang.String thankYouMessage) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void ThankYouScreen(java.lang.String message, com.erankup.customersatisfaction.ui.vote.VoteThemeColors colors) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void EmojiVoteButton(com.erankup.customersatisfaction.domain.model.VoteType voteType, kotlin.jvm.functions.Function1<? super com.erankup.customersatisfaction.domain.model.VoteType, kotlin.Unit> onVoteSelected, com.erankup.customersatisfaction.ui.vote.VoteThemeColors colors, androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.annotation.DrawableRes()
    private static final int drawableRes(com.erankup.customersatisfaction.domain.model.VoteType $this$drawableRes) {
        return 0;
    }
    
    @androidx.compose.runtime.Composable()
    private static final void FeedbackForm(com.erankup.customersatisfaction.ui.vote.VoteUiState state, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onNameChange, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onPhoneChange, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onEmailChange, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onCommentChange, kotlin.jvm.functions.Function1<? super java.lang.Boolean, kotlin.Unit> onConsentChange, boolean showConsentWarning, com.erankup.customersatisfaction.ui.vote.VoteThemeColors colors) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void AdminPasswordDialog(java.lang.String password, boolean isError, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onPasswordChange, kotlin.jvm.functions.Function0<kotlin.Unit> onConfirm, kotlin.jvm.functions.Function0<kotlin.Unit> onCancel) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void AdminPanelDialog(java.lang.String initialToken, java.lang.String initialOwner, java.lang.String initialBaseUrl, java.lang.String initialHeaderTitle, java.lang.String initialHeaderLogoPath, java.lang.String initialHeaderBackgroundColor, java.lang.String initialHeaderTextColor, java.lang.String initialBodyBackgroundColor, java.lang.String initialBodyTextColor, java.lang.String initialAccentColor, kotlin.jvm.functions.Function10<? super java.lang.String, ? super java.lang.String, ? super java.lang.String, ? super java.lang.String, ? super java.lang.String, ? super java.lang.String, ? super java.lang.String, ? super java.lang.String, ? super java.lang.String, ? super java.lang.String, kotlin.Unit> onSave, kotlin.jvm.functions.Function10<? super java.lang.String, ? super java.lang.String, ? super java.lang.String, ? super java.lang.String, ? super java.lang.String, ? super java.lang.String, ? super java.lang.String, ? super java.lang.String, ? super java.lang.String, ? super java.lang.String, kotlin.Unit> onExit, kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss, kotlin.jvm.functions.Function0<kotlin.Unit> onPickLogo, kotlin.jvm.functions.Function0<kotlin.Unit> onRemoveLogo) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void ErrorHint(java.lang.String text) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void ColorInputField(java.lang.String value, java.lang.String label, boolean isError, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onValueChange) {
    }
    
    private static final java.lang.String normalizeColorInput(java.lang.String value) {
        return null;
    }
    
    private static final androidx.compose.ui.graphics.Color parsedColorOrNull(java.lang.String value) {
        return null;
    }
    
    private static final android.app.Activity findActivity(android.content.Context $this$findActivity) {
        return null;
    }
    
    @androidx.compose.runtime.Composable()
    private static final void LogoPreviewSection(java.lang.String logoPath, kotlin.jvm.functions.Function0<kotlin.Unit> onPickLogo, kotlin.jvm.functions.Function0<kotlin.Unit> onRemoveLogo) {
    }
}