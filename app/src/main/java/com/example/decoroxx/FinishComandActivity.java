package com.example.decoroxx;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.creativityapps.gmailbackgroundlibrary.BackgroundMail;

import java.util.List;

public class FinishComandActivity extends AppCompatActivity {
    static DatabaseHandler databaseHandler;
    int totalSuma;
    EditText coments;
    List<Product> productList;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedBundleInstance) {
        super.onCreate(savedBundleInstance);
        setContentView(R.layout.finish_comand_activity);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int heightScreen = displayMetrics.heightPixels;
        int widthScreen = displayMetrics.widthPixels;

        databaseHandler = new DatabaseHandler(this);
        totalSuma = (int) getIntent().getSerializableExtra("totalComanda");

        productList = databaseHandler.getProductShop();
        for (Product product : productList)
            System.out.println(product.toString());

        TextView view = (TextView)findViewById(R.id.finish_txt);
        ScrollView scrollView = (ScrollView)findViewById(R.id.container_finish);
        View bottom_component = (View)findViewById(R.id.bottom_component_finish);
        View shop_total_price = (View)bottom_component.findViewById(R.id.shop_total_price);
        TextView totalPrice = (TextView)findViewById(R.id.total_price);
        coments = (EditText)findViewById(R.id.coments);

        totalPrice.setText(totalSuma + " Lei");

        ViewGroup.LayoutParams paramsTextView = (ViewGroup.LayoutParams)view.getLayoutParams();
        ViewGroup.LayoutParams paramsScrollView = (ViewGroup.LayoutParams)scrollView.getLayoutParams();
        ViewGroup.LayoutParams paramsBottomComponent = (ViewGroup.LayoutParams)bottom_component.getLayoutParams();

        paramsBottomComponent.height = (heightScreen / 4);
        paramsBottomComponent.width = widthScreen;
        bottom_component.setLayoutParams(paramsBottomComponent);

        heightScreen = heightScreen - (heightScreen / 4);

        paramsScrollView.height = (heightScreen * 3) / 4;
        paramsScrollView.width = widthScreen;
        scrollView.setLayoutParams(paramsScrollView);

        heightScreen = heightScreen - ((heightScreen * 3) / 4);

        paramsTextView.height = heightScreen;
        paramsTextView.width = widthScreen;
        view.setLayoutParams(paramsTextView);

        Button sendBtn = (Button)bottom_component.findViewById(R.id.continua);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onClick(View v) {
               alertDialogMessage();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void alertDialogMessage() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Finalizare comanda !");
        TextView textView = new TextView(this);
        textView.setText("\tAtentie !!!\n\n\t\tDupa finalizarea comenzii nu se mai pot face modificari, in comentarii puteti adauga culori in plus !");
        textView.setTextAlignment(TextView.TEXT_ALIGNMENT_TEXT_START);
        textView.setPadding(8,0,4,0);
        dialog.setPositiveButton("Finalizare",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        String[] informationFields = new String[] {"nameField", "phoneField", "addressField"};
                        String[] fields = new String[]{"Nume si Prenume: ", "Telefon: ", "Adresa: "};
                        String message = "Total comanda: " + totalSuma +"\n\n";
                        for (int i = 0; i < 3; i++) {
                            message += fields[i] + getIntent().getSerializableExtra(informationFields[i]).toString().trim() + "\n";
                        }
                        message += "\n";

                        if (!coments.getText().toString().isEmpty())
                            message += "Comentarii: " + coments.getText().toString();
                        message += "\n";

                        message += "+++++++Produse comandate+++++++\n";

                       for (int i = 0; i < productList.size(); i++) {
                           message += "\n===========================================================================================\n";
                           message += "Id produs: " + productList.get(i).getIdProduct() + "\n";
                           message += "Titlu produs: " + productList.get(i).getProductTitle() + "\n";
                           message += "Pret pe bucata: " + productList.get(i).getPrice() + "\n";
                           message += "Cantitate: " + productList.get(i).getQuantity() + "\n";
                           message += "Culoare aleasa: " + productList.get(i).getSelectedColor() + "\n";
                           message += "Culori disponibile: " + productList.get(i).getColorList() + "\n";
                           message += "\n===========================================================================================\n";
                       }

                        sendMail(message);
                      }
                });
        dialog.setNegativeButton("Anulare",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.setView(textView);
        AlertDialog alertDialog=dialog.create();
        alertDialog.show();
    }

    public void sendMail(String message) {
        BackgroundMail.newBuilder(this)
                .withUsername("decoroxxapp@gmail.com")
                .withPassword("2020decoroxxapp2020")
                .withMailto("robimitidoi@gmail.com")
                .withType(BackgroundMail.TYPE_PLAIN)
                .withSubject("Comanda")
                .withBody(message)
                .withOnSuccessCallback(new BackgroundMail.OnSuccessCallback() {
                    @Override
                    public void onSuccess() {
                        Intent goToFinish = new Intent(getApplicationContext(), ThankYouActivity.class);
                        startActivity(goToFinish);
                    }
                })
                .withOnFailCallback(new BackgroundMail.OnFailCallback() {
                    @Override
                    public void onFail() {

                    }
                }).send();

    }
}
