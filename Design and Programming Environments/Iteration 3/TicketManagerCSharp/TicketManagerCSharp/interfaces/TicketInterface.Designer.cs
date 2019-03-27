namespace TicketManagerCSharp
{
    partial class TicketInterface
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.ticketSellButton = new System.Windows.Forms.Button();
            this.buyerTextBox = new System.Windows.Forms.TextBox();
            this.buyerLabel = new System.Windows.Forms.Label();
            this.nrTicketsNumeric = new System.Windows.Forms.NumericUpDown();
            this.nrTicketsLabel = new System.Windows.Forms.Label();
            this.totalPriceLabel = new System.Windows.Forms.Label();
            this.priceLabel = new System.Windows.Forms.Label();
            ((System.ComponentModel.ISupportInitialize)(this.nrTicketsNumeric)).BeginInit();
            this.SuspendLayout();
            // 
            // ticketSellButton
            // 
            this.ticketSellButton.Location = new System.Drawing.Point(12, 131);
            this.ticketSellButton.Margin = new System.Windows.Forms.Padding(4);
            this.ticketSellButton.Name = "ticketSellButton";
            this.ticketSellButton.Size = new System.Drawing.Size(318, 28);
            this.ticketSellButton.TabIndex = 1;
            this.ticketSellButton.Text = "Sell Ticket";
            this.ticketSellButton.UseVisualStyleBackColor = true;
            this.ticketSellButton.Click += new System.EventHandler(this.ticketSellButton_Click);
            // 
            // buyerTextBox
            // 
            this.buyerTextBox.Location = new System.Drawing.Point(169, 11);
            this.buyerTextBox.Margin = new System.Windows.Forms.Padding(4);
            this.buyerTextBox.Name = "buyerTextBox";
            this.buyerTextBox.Size = new System.Drawing.Size(161, 22);
            this.buyerTextBox.TabIndex = 2;
            // 
            // buyerLabel
            // 
            this.buyerLabel.AutoSize = true;
            this.buyerLabel.Font = new System.Drawing.Font("Microsoft Sans Serif", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.buyerLabel.Location = new System.Drawing.Point(9, 11);
            this.buyerLabel.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.buyerLabel.Name = "buyerLabel";
            this.buyerLabel.Size = new System.Drawing.Size(105, 18);
            this.buyerLabel.TabIndex = 3;
            this.buyerLabel.Text = "Name of buyer";
            // 
            // nrTicketsNumeric
            // 
            this.nrTicketsNumeric.Location = new System.Drawing.Point(169, 51);
            this.nrTicketsNumeric.Margin = new System.Windows.Forms.Padding(4);
            this.nrTicketsNumeric.Name = "nrTicketsNumeric";
            this.nrTicketsNumeric.Size = new System.Drawing.Size(161, 22);
            this.nrTicketsNumeric.TabIndex = 4;
            // 
            // nrTicketsLabel
            // 
            this.nrTicketsLabel.AutoSize = true;
            this.nrTicketsLabel.Font = new System.Drawing.Font("Microsoft Sans Serif", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.nrTicketsLabel.Location = new System.Drawing.Point(9, 51);
            this.nrTicketsLabel.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.nrTicketsLabel.Name = "nrTicketsLabel";
            this.nrTicketsLabel.Size = new System.Drawing.Size(125, 18);
            this.nrTicketsLabel.TabIndex = 5;
            this.nrTicketsLabel.Text = "Number of tickets";
            // 
            // totalPriceLabel
            // 
            this.totalPriceLabel.AutoSize = true;
            this.totalPriceLabel.Font = new System.Drawing.Font("Microsoft Sans Serif", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.totalPriceLabel.Location = new System.Drawing.Point(9, 96);
            this.totalPriceLabel.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.totalPriceLabel.Name = "totalPriceLabel";
            this.totalPriceLabel.Size = new System.Drawing.Size(77, 18);
            this.totalPriceLabel.TabIndex = 6;
            this.totalPriceLabel.Text = "Total price";
            // 
            // priceLabel
            // 
            this.priceLabel.AutoSize = true;
            this.priceLabel.Location = new System.Drawing.Point(165, 98);
            this.priceLabel.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.priceLabel.Name = "priceLabel";
            this.priceLabel.Size = new System.Drawing.Size(0, 17);
            this.priceLabel.TabIndex = 7;
            // 
            // TicketInterface
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(347, 172);
            this.Controls.Add(this.priceLabel);
            this.Controls.Add(this.totalPriceLabel);
            this.Controls.Add(this.nrTicketsLabel);
            this.Controls.Add(this.nrTicketsNumeric);
            this.Controls.Add(this.buyerLabel);
            this.Controls.Add(this.buyerTextBox);
            this.Controls.Add(this.ticketSellButton);
            this.Margin = new System.Windows.Forms.Padding(4);
            this.Name = "TicketInterface";
            this.Text = "TicketInterface";
            ((System.ComponentModel.ISupportInitialize)(this.nrTicketsNumeric)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion
        private System.Windows.Forms.Button ticketSellButton;
        private System.Windows.Forms.TextBox buyerTextBox;
        private System.Windows.Forms.Label buyerLabel;
        private System.Windows.Forms.NumericUpDown nrTicketsNumeric;
        private System.Windows.Forms.Label nrTicketsLabel;
        private System.Windows.Forms.Label totalPriceLabel;
        private System.Windows.Forms.Label priceLabel;
    }
}